/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio.run;

import java.util.GregorianCalendar;
import br.com.util.relogio.observador.Observer;
import br.com.util.relogio.observador.Subject;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class RunContador implements Runnable, Observer {

    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;
    private Boolean statos = true;
    private Subject observer;
    private GregorianCalendar data = new GregorianCalendar();

    public RunContador(int ano, int mes, int dia, int hora, int minuto, int segundo, Subject observer) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
        this.observer = observer;
    }

    @Override
    public void run() {
        try {
            while (statos == true) {
                // Segundos
                if (this.segundo < 60) {
                    this.segundo++;
                } else if (this.segundo == 60) {
                    this.segundo = 1;
                    this.minuto++;
                }

                // Minutos
                if (this.minuto > 59) {
                    this.minuto = 0;
                    this.hora++;
                }

                // Horas
                if (this.hora == 24) {
                    this.hora = 0;
                    this.dia++;
                }

                data.set(ano, mes, dia, hora, minuto, segundo);
                observer.notifyObserversDados(data);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        } finally {
            statos = null;
        }
    }

    public Boolean statos() {
        return statos;
    }

    @Override
    public void actionStatos(boolean statos) {
        this.statos = statos;
    }

    @Override
    public void actionDados(GregorianCalendar dataTime) {
    }
}
