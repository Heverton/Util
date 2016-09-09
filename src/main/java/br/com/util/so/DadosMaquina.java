package br.com.util.so;

import br.com.util.arquivo.ManipulaArquivoTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.FileLock;
import javax.swing.JTextArea;

/**
 * Responsavel por manipula as porta do sistema fornecendo lista de porta.
 *
 * @author Heverton da Silva Cruz, Herberts da Silva Cruz
 * @version 1.1
 */
public abstract class DadosMaquina {

    public static final String ARQUITETURASO = System.getProperty("os.arch").toString();
    public static final String LOCALJAR = System.getProperty("user.dir").toString();
    public static final String VERSAOJAVA = System.getProperty("java.vm.specification.version").toString();
    public static final int MODELOARQUITETURALSO = Integer.parseInt(System.getProperty("sun.arch.data.model").toString());
    public static final String NOMESO = System.getProperty("os.name").toString();

    /**
     * Retorna o diretório "Arquivos de Programas" para programas em 64 bits.
     *
     * @return
     */
    public static File getProgramFiles64() throws NullPointerException {
        //Se o OS do usuário da classe não for de 64 bits
        if (MODELOARQUITETURALSO != 64) {
            throw new NullPointerException("O seu sistema operacional não é de 64 bits.");
        }
        //Se o nome da versão for
        switch (DadosMaquina.NOMESO) {
            case "Windows XP":
                return new File("C:\\ARQUIV~1");
            default: //Windows 7
                return new File("C:\\PROGRA~1");
        }
    }

    /**
     * Retorna o diretório "Arquivos de Programas" para programas em 32 bits.
     *
     * @return
     */
    public static File getProgramFiles32() throws NullPointerException  {
        //Se o nome da versão for
        switch (DadosMaquina.NOMESO) {
            case "Windows XP":
                return (ARQUITETURASO.equals("amd64")) ? new File("C:\\ARQUIV~2") : new File("C:\\ARQUIV~1");
            default: //Windows 7
                return (ARQUITETURASO.equals("amd64")) ? new File("C:\\PROGRA~2") : new File("C:\\PROGRA~1");
        }
    }

    public static boolean unicaInstancia(String nome) throws IOException {
        File f = new File(nome + ".lock");
        f.createNewFile();
        FileLock lock = new RandomAccessFile(f, "rw").getChannel().tryLock();

        if (lock != null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean verificarProcessosJava(String nomeProcesso) {
        int iRetorno = 0;
        boolean statos = false;

        try {
            Process p = Runtime.getRuntime().exec("jps");

            InputStream is = p.getInputStream();
            InputStreamReader readerInput = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(readerInput);
            p.waitFor();
            iRetorno = p.exitValue();

            String linha = "";

            while ((linha = reader.readLine()) != null) {
                try {
                    if (linha.contains(nomeProcesso)) {
                        statos = true;
                    }
                } catch (Exception e) {
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new NullPointerException(e.getMessage());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
        }

        if (statos) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Não nercessida de nome para o arquivo
     * Mas quando utilizado com outro programas na mesma pasta e recomendavel a utilização
     * 
     * @param nomeServico
     * @return 
     */
    public static boolean executarScriptUnicaInstanciaAplicacao(String nomeServico) {
        try {
            String line = "cmd /c taskList";
            Process p = Runtime.getRuntime().exec(line);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            int cont = 0;

            while ((line = input.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    if (line.split(" ")[0].equals(nomeServico)) {
                        cont++;
                    }

                    // Numero de ocorências da instancia.
                    if (cont > 1) {
                        return true;
                    }
                }
            }

            input.close();

            return false;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
        }
    }

    /**
     * Utilizada para testar um ip na rede retorno uma strig com as informações testadas
     * 
     * @param ip
     * @return 
     */
    public static String testaIP(String ip) {
        String lista = "";

        try {
            String line = "cmd /c ping " + ip;
            Process p = Runtime.getRuntime().exec(line);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));

            while ((line = input.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lista += "\n" + line;
                }
            }
            input.close();

            return lista;
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Testa o IP com a classe InetAdress.
     *
     * @param ip
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress testaIPInetAddress(String ip) throws UnknownHostException {
        String[] posicoesIp = ip.split("\\.");

        byte[] btIp = new byte[4];
        //Cria o array de byte de acordo com o exigido pelo método do InetAddress
        for (int i = 0; i < posicoesIp.length; i++) {
            int psIp = Integer.parseInt(posicoesIp[i]);

            if (psIp > 127) {
                btIp[i] = Byte.parseByte((psIp - 256) + "");
            } else {
                btIp[i] = Byte.parseByte(psIp + "");
            }
        }
        return InetAddress.getByAddress(btIp);
    }

    /**
     * Responsável por retorar um objeto String
     *
     * @return string com o ip
     */
    public static String ipString() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public static boolean isValidaEnderecoIPV4eIPV6(String address) {
        boolean isValid = false;

        // Verififica campo vazio
        if (address == null || address.trim().isEmpty()) {
            return isValid;
        }

        try {
            // Verifica se é do tipo IPV6
            isValid = InetAddress.getByName(address) instanceof Inet6Address;

            if (isValid == false) {
                // Verifica se é do tipo IPV4
                isValid = InetAddress.getByName(address) instanceof InetAddress;
            }
        } catch (UnknownHostException ex) {
            isValid = false;
        }

        return isValid;
    }

    public static boolean eliminarProcessosJava(String nomeProcesso) {
        int iRetorno = 0;

        try {
            Process p = Runtime.getRuntime().exec("jps");

            InputStream is = p.getInputStream();
            InputStreamReader readerInput = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(readerInput);
            p.waitFor();
            iRetorno = p.exitValue();

            String linha = "";

            while ((linha = reader.readLine()) != null) {
                try {
                    if (linha.contains(nomeProcesso)) {
                        String[] getID = linha.split(" ");
                        Runtime.getRuntime().exec("taskkill /f /pid " + getID[0]);
                    }
                } catch (Exception e) {
                    //Silência para evitar erros
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new NullPointerException(e.getMessage());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
        }

        if (iRetorno == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean executarProcessosNotificacao(JTextArea area, String script) {
        try {
            Process p = Runtime.getRuntime().exec(script);

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));

            String line = "";

            while ((line = input.readLine()) != null) {
                if (line.trim() != null || !line.isEmpty()) {
                    area.append(line.trim() + " OK \n");
                }
            }

            area.append("Concluído");

            input.close();

            p.waitFor();

            return true;
        } catch (IOException | InterruptedException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Disparar comando assíncrono
     *
     * @param script
     * @return
     */
    public static boolean executarProcessosExterno(File script) {
        int iRetorno = 0;

        try {
            Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL cmd /c start /min " + script.toString() + "  ");

            (new StreamGobbler(p.getErrorStream(), "ERRO")).start();
            (new StreamGobbler(p.getInputStream(), "WAR")).start();

            iRetorno = p.exitValue();

            // Deletar arquivo já usado
            script.delete();

            p.destroy();
        } catch (Exception e) {
            iRetorno = 0;
        } finally {
        }

        if (iRetorno == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Disparar comando assíncrono
     *
     * @param script
     * @return
     */
    public static boolean executarProcessosExterno(String script) {
        int iRetorno = 0;

        try {
            Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL cmd /c start /min " + script + "  ");

            (new StreamGobbler(p.getErrorStream(), "ERRO")).start();
            (new StreamGobbler(p.getInputStream(), "WAR")).start();

            iRetorno = p.exitValue();

            p.destroy();
        } catch (Exception e) {
            iRetorno = 0;
        } finally {
        }

        if (iRetorno == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * O comando assíncrono e reinicia a aplicação utilizando uma dll integrada
     * ao arquivo principal de boot. Obrigatório o arquivo hstart.exe
     *
     * Exemplo:
     *
     * Arquivo obrigadório: c:\host\pdv\hstart.exe c:\host\pdv\host.jar
     *
     * @param nomeSistema
     * @throws java.io.IOException
     */
    public static void reiniciarSistema(String nomeSistema) throws IOException {

        File caminho = new File(LOCALJAR + File.separator + "reboot.bat");
        // Deletar arquivo já usado
        caminho.delete();

        // Configuração do bat de boot
        ManipulaArquivoTxt ma = new ManipulaArquivoTxt(caminho);
        ma.addLinha("@echo off");
        ma.addLinha("java -jar " + LOCALJAR + File.separator + nomeSistema);
        ma.renovaCache();

        // Start no arquivo 
        try {
            Process p = Runtime.getRuntime().exec("cmd /c " + LOCALJAR + File.separator + "hstart.exe /NOCONSOLE " + caminho.toString() + " ");
            p.exitValue();
            p.destroy();
        } catch (IOException e) {
        } finally {
        }
    }

    /**
     * Disparar arquivo síncrono
     *
     * @param script
     * @return
     */
    public static boolean executarProcessos(String script) {
        int iRetorno = 0;

        try {
            Process p = Runtime.getRuntime().exec(script);

            (new StreamGobbler(p.getInputStream(), "INFO")).start();

            p.waitFor();

            iRetorno = p.exitValue();

            p.destroy();
        } catch (IOException | InterruptedException | IllegalMonitorStateException e) {
            iRetorno = 0;
        } finally {
        }

        if (iRetorno == 0) {
            return true;
        } else {
            return false;
        }
    }
}
