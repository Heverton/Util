package br.com.util.arquivo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * Classe responsável por criar, adcionar, ler arquivos todos os tipos.
 *
 * @author Heverton da Silva Cruz, Herberts Cruz
 * @version 2.1
 */
public class ManipulaArquivoTxt extends ManipulaArquivo {
    
    /**
     * Instância um manipulador do arquivo enviado.
     *
     * @param arquivo
     */
    public ManipulaArquivoTxt(File arquivo) 
            throws FileNotFoundException, IOException {
        this(arquivo, false);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção informar a 
     * codificação do arquivo que será manipulado.
     *
     * @param arquivo
     * @param codificacao
     */
    public ManipulaArquivoTxt(File arquivo, Codificacao codificacao) 
            throws FileNotFoundException, IOException {
        super(arquivo, codificacao);
    }

    /**
     * Instância um manipulador do arquivo enviado, dando a opção de remover as
     * linhas que estiverem em branco quando ele for lido.
     *
     * @param arquivo
     * @param isRemoveLinhasVazias
     */
    public ManipulaArquivoTxt(File arquivo, boolean isRemoveLinhasVazias) 
            throws FileNotFoundException, IOException {
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
    public ManipulaArquivoTxt(File arquivo, Codificacao codificacao, boolean isRemoveLinhasVazias) 
            throws FileNotFoundException, IOException {
        super(arquivo, isRemoveLinhasVazias);
    }

    /**
     * Pega a quantidade de linhas do arquivo.
     *
     * @return
     */
    public int getQuantidadeLinhas() {
        return linhas.size();
    }

    /**
     * Retorna a linha solicitada.
     *
     * @param indiceLinha
     * @return String
     */
    public String getLinha(int indiceLinha) {
        if (indiceLinha < 0) {
            throw new IndexOutOfBoundsException("O índice da linha não pode ser menor que zero.");
        } else if (linhas.size() < indiceLinha) {
            throw new IndexOutOfBoundsException("O índice da linha não pode ser maior que a quantidade de linhas existentes.");
        } else {
            return linhas.get(indiceLinha);
        }
    }

    /**
     * Retorna todas as linhas do arquivo em uma lista.
     *
     * @return List<String>
     */
    public List<String> getLinhas() {
        return getLinhas(-1, -1);
    }

    /**
     * Retorna todas as linhas do arquivo em uma lista no intervalo desejado.
     *
     * @param indiceLinhaInicial
     * @param indiceLinhaFinal
     * @return List<String>
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String> getLinhas(int indiceLinhaInicial, int indiceLinhaFinal) {
        if (indiceLinhaInicial != -1 && indiceLinhaFinal != -1) {
            if (indiceLinhaInicial > indiceLinhaFinal) {
                throw new IndexOutOfBoundsException("O índice da linha inicial não pode ser maior que o índice da linha final.");
            } else if (indiceLinhaFinal == 0) {
                throw new IndexOutOfBoundsException("O índice da linha final não pode ser zero.");
            } else if (indiceLinhaFinal > linhas.size()) {
                throw new IndexOutOfBoundsException("O índice da linha final solicitada não pode ser maior que o número de linhas existente.");
            } else {
                return linhas.subList(indiceLinhaInicial, indiceLinhaFinal);
            }
        } else {
            return linhas;
        }
    }

    /**
     * Escreve o dado na última linha do arquivo.
     *
     * @param dado
     * @throws IOException
     */
    public void addLinha(String dado) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(dado);
        conteudo.append(newLine);
        
        escrever(conteudo, true);
    }

    /**
     * Escreve os dados da lista considerando cada posição dela como uma linha
     * do arquivo. Se houverem dados no arquivo, eles seram apagados.
     *
     * @param dados
     * @throws IOException
     */
    public void addLinhas(List<String> dados) throws IOException {
        addLinhas(dados, false);
    }

    /**
     * Escreve os dados da lista considerando cada posição dela como uma linha
     * do arquivo. Dá a opção de escolher se deseja ou não colocar o conteúdo
     * após a última linha.
     *
     * @param dados
     * @param isEscreverAposUltimaLinha
     * @throws IOException
     */
    public void addLinhas(List<String> dados, boolean isEscreverAposUltimaLinha) throws IOException {
        StringBuilder conteudo = new StringBuilder();

        for (String dado : dados) {
            conteudo.append(dado);
            conteudo.append(newLine);
        }

        escrever(conteudo, isEscreverAposUltimaLinha);
    }
    
    /**
     * Escreve os dados do campo considerando cada posição dela como uma linha
     * do arquivo. Dá a opção de escolher se deseja ou não colocar o conteúdo
     * após a última linha.
     *
     * @param dados
     * @param isEscreverAposUltimaLinha
     * @throws IOException
     */
    public void addLinha(String dados, boolean isEscreverAposUltimaLinha) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(dados);
        conteudo.append(newLine);        
        escrever(conteudo, isEscreverAposUltimaLinha);
    }
    
    /**
     * Renova o cache do arquivo.
     */
    public final void renovaCache() throws FileNotFoundException, IOException {
        ler();
    }
}
