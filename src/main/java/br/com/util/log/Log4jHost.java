/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import br.com.util.data.DataHora;
import br.com.util.so.DadosMaquina;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Realiza a gravação em um arquivo tipo .log
 *
 * @author Heverton
 * @version 1.0
 */
public class Log4jHost {

    private static LoggerUtil instance;

    /**
     * Registra a classe e devolve uma instancea de Logger
     *
     * Nível (1) DEBUG - Uso para Desenvolvimento. Nível (2) INFO - Informar
     * mensagens. Nível (3) WARN - Alerta indicio de erro. Nível (4) ERROR -
     * Erro encontado na aplicação. Nível (5) FATAL - Ouve um erro grave na
     * aplicação e parou a estrutura.
     *
     * @param classe
     * @return Logger
     */
    @Deprecated
    public static LoggerUtil getLog(Class<?> classe) {

        if (instance == null) {
            instance = configureLog(classe);
        }

        return instance;
    }

    /**
     * Registra a classe e devolve uma instancea de Logger
     *
     * Nível (1) DEBUG - Uso para Desenvolvimento. Nível (2) INFO - Informar
     * mensagens. Nível (3) WARN - Alerta indicio de erro. Nível (4) ERROR -
     * Erro encontado na aplicação. Nível (5) FATAL - Ouve um erro grave na
     * aplicação e parou a estrutura.
     *
     * É para caso de multitread Sincronizado.
     * 
     * @param classe
     * @return Logger
     */
    public synchronized static LoggerUtil getInstance(Class<?> classe) {
        
        if (instance == null) {
            instance = configureLog(classe);
        }

        return instance;
    }
    

    /**
     * Realiza a configuração
     *
     * @param classe
     * @return
     */
    private static LoggerUtil configureLog(Class<?> classe) {
        // Seta novo objeto
        instance = new LoggerUtil(Logger.getLogger(classe.getName()));

        // Seta o nivel de log que será registrado
        instance.getLogger().setLevel(Level.ALL);

        String config = "";
        config += "[%d{dd/MM/yyyy} %d{HH:mm:ss}] ";
        config += "[%5p] ";
        config += "[" + classe.getCanonicalName() + "] ";
        config += "[%t] ";
        config += "[LIN: %L] - MSG: %m %n";
        
        // Criar um layout para o log
        PatternLayout layout = new PatternLayout(config);

        // Criar a pasta log se não estiver sido criada.
        File caminho = new File(DadosMaquina.LOCALJAR + File.separator + "log");
        if (!caminho.exists()) {
            caminho.mkdirs();
        }

        FileAppender fileAppender = null;
        ConsoleAppender telaAppender = null;
        try {
            String stringCaminho = "log" + File.separator + DataHora.converterDateEmString(new Date(), DataHora.Formato.EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS) + ".log";

            // Pega o tamanho do arquivo em bytes
            long fileSizeInBytes = (new File(stringCaminho)).length();
            // Converte o byte para Kilobytes (1 KB = 1024 Bytes)
            long fileSizeInKB = fileSizeInBytes / 1024;

            // Faz um backup do log
            if (fileSizeInKB >= 10000) {
                File ar = new File(stringCaminho);
                ar.renameTo(new File("log" + File.separator + DataHora.converterDateEmString(new Date(), DataHora.Formato.EN_DATA_SEPARADOR_TRACO_ANO_4DIGITOS) + "_" + DataHora.converterDateEmString(new Date(), DataHora.Formato.TEMPO_HORA_MINUTOS_SEGUNDOS).replace(":", "") + ".log"));
                ar.delete();
            }

            // Criar log Diário
            fileAppender = new FileAppender(layout, stringCaminho, true);
            telaAppender = new ConsoleAppender(layout);

        } catch (IOException ex) {
            System.err.println("Erro log:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            instance.getLogger().addAppender(fileAppender);
            instance.getLogger().addAppender(telaAppender);
        }

        return instance;
    }
}
