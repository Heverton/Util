package br.com.util.arquivo;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manipula arquivos criados a partir de objetos serializados.
 *
 * @author Herberts Cruz
 * @author Heverton Cruz
 * @version 1.0
 *
 * @param <T>
 */
public class ManipulaArquivoDeObjeto<T extends Object> extends ManipulaArquivo {

    private List<Object> objetos;

    /**
     * Instância um manipulador do arquivo enviado.
     *
     * @param arquivo
     * @throws java.io.FileNotFoundException
     */
    public ManipulaArquivoDeObjeto(File arquivo) throws FileNotFoundException, IOException {
        this(arquivo, true);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção informar a
     * codificação do arquivo que será manipulado.
     *
     * @param arquivo
     * @param codificacao
     */
    private ManipulaArquivoDeObjeto(File arquivo, Codificacao codificacao) throws FileNotFoundException, IOException {
        super(arquivo, codificacao);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção de remover as
     * linhas que estiverem em branco quando ele for lido.
     *
     * @param arquivo
     * @param isRemoveLinhasVazias
     */
    private ManipulaArquivoDeObjeto(File arquivo, boolean isRemoveLinhasVazias) throws FileNotFoundException, IOException {
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
    private ManipulaArquivoDeObjeto(File arquivo, Codificacao codificacao, boolean isRemoveLinhasVazias) throws FileNotFoundException, IOException {
        super(arquivo, codificacao, isRemoveLinhasVazias);
    }

    @Override
    protected void ler() throws FileNotFoundException, IOException {
        try {
            try (ObjectInputStream oos = new java.io.ObjectInputStream(new FileInputStream(arquivo))) {
                objetos = (List<Object>) oos.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (EOFException e) {
            // Gera um erro mais compila normalmente 
        }
    }

    /**
     * Retorna o objeto já deserializado do arquivo que está sendo manipulado.
     *
     * @return String
     */
    public T getObjeto() {
        return getObjeto(false, false);
    }

    /**
     * Retorna o objeto já deserializado do arquivo que está sendo manipulado.
     *
     * @param isCache
     * @return String
     */
    public T getObjeto(boolean isCache) {
        return getObjeto(isCache, false);
    }

    private T getObjeto(boolean isCache, boolean bs) {
        try {
            // Atualizar cache
            if (isCache) {
                ler();
            }
            return (T) objetos.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retorna o objeto já deserializado do arquivo que está sendo manipulado.
     * Realiza um novo carregamento
     *
     * @param isCache
     * @return String
     *
     * @throws java.io.IOException
     */
    public List<T> getObjetos(boolean isCache) throws IOException {
        return getObjetos(isCache, false);
    }

    /**
     * Retorna o objeto já deserializado do arquivo que está sendo manipulado.
     * Realiza um novo carregamento
     *
     * @return String
     *
     * @throws java.io.IOException
     */
    public List<T> getObjetos() throws IOException {
        return getObjetos(false, false);
    }

    private List<T> getObjetos(boolean isCache, boolean bs) throws IOException {
        if (isCache) {
            //Realiza uma leitura renoção de cache
            ler();
        }

        return (List<T>) objetos;
    }

    /**
     * Cria um arquivo cujo conteúdo é a instância do objeto enviado
     * serializado.
     *
     * Caso precise de mais objetos coloqueos em um array
     *
     * @param objeto
     * @throws IOException
     */
    public void criarArquivoDoobjeto(Object objeto) throws IOException {
        List<Object> objects = new ArrayList<>();
        objects.add(objeto);

        try (ObjectOutputStream oos = new java.io.ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(objects);
            oos.flush();
        }
    }

    public void criarArquivoDoobjeto(Object... objetos) throws IOException {
        //Adciona os parametros
        List<Object> objects1 = new ArrayList<>();
        objects1.addAll(Arrays.asList(objetos));

        try (ObjectOutputStream oos = new java.io.ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(objects1);
            oos.flush();
        }
    }

    public void criarArquivoDoobjeto(List<Object> objetos) throws IOException {
        try (ObjectOutputStream oos = new java.io.ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(objetos);
            oos.flush();
        }
    }
    
    /**
     * Renova o cache do arquivo.
     */
    public final void renovaCache() throws FileNotFoundException, IOException {
        ler();
    }
}
