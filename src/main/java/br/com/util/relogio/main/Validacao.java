/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio.main;

import java.util.GregorianCalendar;
import br.com.util.relogio.observador.ControllerObserver;
import br.com.util.relogio.observador.Observer;
import br.com.util.relogio.observador.Subject;
import br.com.util.relogio.run.RunContador;

/**
 *
 * @author Heverton
 * @version 1.0
 */
public class Validacao implements Observer {

    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;
    private GregorianCalendar data = new GregorianCalendar();
    private RunContador runControler;
    private static Validacao instance;
    private static Subject observador;

    private Validacao() {
    }

    public static Validacao getInstance() {
        if (instance == null) {
            instance = new Validacao();
            ControllerObserver.getInstance().registreObserver(instance);
            observador = ControllerObserver.getInstance().getObserver();
        }

        return instance;
    }

    public boolean start() {
        try {
            if (runControler != null) {
                throw new Exception();
            }

            runControler = new RunContador(ano, mes, dia, hora, minuto, segundo, ControllerObserver.getInstance().getObserver());
            // Inicia thread de contador.
            observador.runAction(runControler);

            return true;
        } catch (Exception e) {
            return false;
        } finally {
        }
    }

    public boolean restart() {
        try {
            if (runControler != null) {
                throw new Exception();
            }

            // Inicializa outro
            runControler = new RunContador(ano, mes, dia, hora, minuto, segundo, ControllerObserver.getInstance().getObserver());

            // Inicia thread de contador.
            observador.runAction(runControler);

            return true;
        } catch (Exception e) {
            return false;
        } finally {
        }
    }

    @SuppressWarnings("SleepWhileInLoop")
    public boolean stop() {
        try {
            // Finaliza atual
            runControler.actionStatos(false);

            while (runControler.statos() != null) {
                Thread.sleep(500);
            }

            runControler = null;

            return true;
        } catch (Exception e) {
            return false;
        } finally {
        }
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(int ano, int mes, int dia, int hora, int minuto, int segundo) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    @Override
    public void actionStatos(boolean statos) {
    }

    @Override
    public void actionDados(GregorianCalendar dataTime) {
        this.data = dataTime;
    }
}
