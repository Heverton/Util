/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio.main;

import java.util.GregorianCalendar;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class Relogio {

    /**
     * Inicia o relógio.
     *
     * @return
     */
    public boolean start() {
        try {
            return Validacao.getInstance().start();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Reiniciar o relógio.
     *
     * @return
     */
    public boolean restart() {
        try {
            return Validacao.getInstance().restart();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Para o relógio.
     *
     * @return
     */
    public boolean stop() {
        try {
            Validacao.getInstance().stop();

            // Aguarda finalizacao
            Thread.sleep(2000);

            return true;
        } catch (InterruptedException ex) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Coletar a data e hora do contador.
     *
     * @return Calendar
     */
    public GregorianCalendar getDataHora() {
        return Validacao.getInstance().getData();
    }

    /**
     * Acertar os ponteiros da data e hora.
     *
     * @param Date dt
     */
    public void acertarPonteros(int ano, int mes, int dia, int hora, int minuto, int segundo) {
        Validacao.getInstance().setData(ano, mes, dia, hora, minuto, segundo);
    }
}
