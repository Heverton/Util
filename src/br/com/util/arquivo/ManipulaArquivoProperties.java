package br.com.util.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * Manipula arquivos .properts
 *
 * @author Heverton Cruz, Herberts Cruz
 * @version 2.0
 */
public class ManipulaArquivoProperties extends ManipulaArquivo {
    
    private Properties propriedades;

    /**
     * Instância um manipulador do arquivo enviado.
     *
     * @param arquivo
     */
    public ManipulaArquivoProperties(File arquivo)  throws FileNotFoundException, IOException {
        this(arquivo, true);
    }
    
    /**
     * Instância um manipulador do arquivo enviado, dando a opção informar a 
     * codificação do arquivo que será manipulado.
     *
     * @param arquivo
     * @param codificacao
     */
    private ManipulaArquivoProperties(File arquivo, Codificacao codificacao)  throws FileNotFoundException, IOException {
        super(arquivo, codificacao);
    }
    
    /**
     * Instância um manipulador do arquivo enviado, dando a opção de remover as
     * linhas que estiverem em branco quando ele for lido.
     *
     * @param arquivo
     * @param isRemoveLinhasVazias
     */
    private ManipulaArquivoProperties(File arquivo, boolean isRemoveLinhasVazias) throws FileNotFoundException, IOException {
        super(arquivo, isRemoveLinhasVazias);
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
    private ManipulaArquivoProperties(File arquivo, Codificacao codificacao, boolean isRemoveLinhasVazias) throws FileNotFoundException, IOException {
        super(arquivo, codificacao, isRemoveLinhasVazias);
    }

    @Override
    protected void ler() throws FileNotFoundException, IOException {
        try (FileInputStream in = new FileInputStream(arquivo)) {
            this.propriedades = new Properties();
            this.propriedades.load(in);
        }
    }
    
    /**
     * Retorna o valor da chave enviada por parametro.
     * 
     * @param chave
     * @return String
     */
    public String getPropriedade(String chave) {
        return this.propriedades.getProperty(chave);
    }
    
    /**
     * Retorna o objeto properties que contém o resultado da leitura do arquivo.
     * 
     * @return Properties
     */
    public Properties getPropriedades() {
        return this.propriedades;
    }

    public void salvar(Properties propriedades) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        
        for (Iterator<Object> it = propriedades.keySet().iterator(); it.hasNext();) {
            Object dado = it.next();
            conteudo.append(dado);
            conteudo.append("=");
            conteudo.append(propriedades.get(dado));
            conteudo.append(newLine);
        }

        escrever(conteudo, false);
        
        this.propriedades = propriedades;
    }
    
    /**
     * Alterar o valor da chave do .properties
     * 
     * @param key
     * @param value 
     */
    public void alterar(String key, String value){
        this.propriedades.setProperty(key, value);
    }
}
