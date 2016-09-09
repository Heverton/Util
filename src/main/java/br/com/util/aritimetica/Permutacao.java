package br.com.util.aritimetica;

/**
 * Classe que define as operacoes de Permutacoes vistas em:
 *
 * http://daemoniolabs.wordpress.com/2011/02/11/
 * gerando-permutacoes-r-com-repeticao-em-c/
 *
 * Qualquer dúvida visite o post acima para obter mais explicações.
 *
 * Autor: Daemonio (Marcos Paulo Ferreira) Contato: undefinido gmail com
 * http://daemoniolabs.wordpress.com
 *
 * Fri Jul 8 10:16:07 BRT 2011
 *
 * @author Heverton Cruz
 * @author Herberts Cruz
 */
public class Permutacao {

    private int[] N;
    private int n;
    private String[] entrada;
    private int r;

    /**
     * entrada é seu vetor de elementos e r é o tamanho da permutacao. Aqui r
     * obrigatoriamente deve ser maior que zero.
     */
    public Permutacao(String[] entrada, int r) {
        this.r = r;
        this.n = entrada.length;
        this.N = new int[r + 1];
        this.entrada = entrada;
    }

    public boolean hasNext() {
        return this.N[this.r] == 0;
    }

    public String[] next() {
        String[] saida = new String[this.r];
        int i, j;

        for (i = 0, j = this.r - 1; i < this.r; i++) {
            saida[j] = entrada[this.N[i]];
            j -= 1;
        }

        this.N[0] += 1;

        for (i = 0; i < this.r; i++) {
            if (this.N[i] == this.n) {
                this.N[i] = 0;
                this.N[i + 1] += 1;
            }
        }
        return saida;
    }
}