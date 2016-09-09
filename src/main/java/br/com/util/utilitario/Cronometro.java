package br.com.util.utilitario;

/**
 * Conta o tempo de execução de um processo.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public class Cronometro {

    public long tempInicial;
    public long tempFinal;

    public void iniciar() {
        tempInicial = System.currentTimeMillis();
    }

    public String finalizar() {
        tempFinal = System.currentTimeMillis();
        //Diferença entre o início e fim
        long dif = (tempFinal - tempInicial);
        //Retorna o resultado formatado
        return String.format("Tempo de execução: %02d segundos  e %02d milisegundos", dif / 1000, dif % 1000);
    }
}
