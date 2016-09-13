/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio.observador;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class ControllerObserver {
    // Instance para Observer

    private static Observado observeado = null;
    // Instance do Padrão Singleton
    private static ControllerObserver instance = null;

    /**
     * Padrão Singleton
     *
     * @return ControllerObserver
     */
    public static ControllerObserver getInstance() {
        if (instance == null) {
            instance = new ControllerObserver();
            observeado = new Observado();
        }

        return instance;
    }

    private void ControllerPdvObserver() {
    }

    public void registreObserver(Observer o) {
        observeado.registreObserver(o);
    }

    public void removeObserver(Observer o) {
        observeado.removeObserver(o);
    }

    public Subject getObserver() {
        return observeado;
    }
}
