package com.hsc.arquivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de interface para leitura e escrita de arquivos.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public abstract class ManipulaArquivo {
    
    protected File arquivo;
    protected Codificacao codificacao;
    protected boolean isRemoveLinhasVazias = false;
    protected String newLine = System.getProperty("line.separator");
    protected List<String> linhas = new ArrayList();
    
    public enum Codificacao {
        UTF8("UTF-8"), ASCII("ISO-8859-1");
        
        private final String valor;
        
        private Codificacao(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }

    /**
     * Instância um manipulador do arquivo enviado.
     *
     * @param arquivo
     */
    public ManipulaArquivo(File arquivo) throws FileNotFoundException, IOException {
        this(arquivo, false);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção informar a 
     * codificação do arquivo que será manipulado.
     *
     * @param arquivo
     * @param codificacao
     */
    public ManipulaArquivo(File arquivo, Codificacao codificacao) throws FileNotFoundException, IOException {
        this(arquivo, codificacao, false);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção de remover as
     * linhas que estiverem em branco quando ele for lido.
     *
     * @param arquivo
     * @param isRemoveLinhasVazias
     * @throws java.io.FileNotFoundException
     */
    public ManipulaArquivo(File arquivo, boolean isRemoveLinhasVazias) throws FileNotFoundException, IOException {
        this(arquivo, Codificacao.UTF8, isRemoveLinhasVazias);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção informar a 
     * codificação do arquivo que será manipulado e remover as linhas que 
     * estiverem em branco quando ele for lido.
     *
     * @param arquivo
     * @param codificacao
     * @param isRemoveLinhasVazias
     */
    public ManipulaArquivo(File arquivo, Codificacao codificacao, boolean isRemoveLinhasVazias) throws FileNotFoundException, IOException {
        this.arquivo = arquivo;
        this.codificacao = codificacao;
        this.isRemoveLinhasVazias = isRemoveLinhasVazias;
        //Cria o arquivo se não existir
        if(!arquivo.exists()) {
            arquivo.createNewFile();
        }
        executaLeitura();
    }
    
    /**
     * Método responsável por fazer o acoplamento da chamada do método ler.
     *
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void executaLeitura() throws FileNotFoundException, IOException {
        ler();
    }
    
    /**
     * Pega o objeto File do arquivo que está sendo manipulado.
     *
     * @return
     */
    public File getArquivo() {
        return arquivo;
    }

    /**
     * Adiciona a linha no list de linhas, removendo as linhas vazias se o
     * parametro for definido como true.
     *
     * @param linha
     */
    private void addListLinhas(String linha) {
        //Se estiver habilitado para remover linhas vazias
        if (isRemoveLinhasVazias) {
            if (!linha.isEmpty()) {
                linhas.add(linha);
            }
        } else {
            linhas.add(linha);
        }
    }

    /**
     * Faz a leitura do arquivo de fato.
     *
     * @return List<String>
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void ler() throws FileNotFoundException, IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), codificacao.getValor()))) {
            linhas.clear();
            String linha;

            while ((linha = in.readLine()) != null) {
                addListLinhas(linha);
            }
        }
    }
    
    /**
     * Executa a escrita no arquivo de fato.
     *
     * @param conteudo
     * @param isAposUltimaLinha
     * @throws IOException
     */
    protected void escrever(StringBuilder conteudo, boolean isAposUltimaLinha) throws IOException {
        try (FileWriter fw = new FileWriter(arquivo, isAposUltimaLinha)) {
            fw.write(conteudo.toString());
            fw.flush();
        }
    }
}
