/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio.observador;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class Observado implements Subject {

    private ArrayList<Observer> observers;
    // Define a variável executorServico para criar o multithread e permite apenas 100 thread
    //private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    //private LimitedExecutorService executorService = new LimitedExecutorService(100);

    public Observado() {
        observers = new ArrayList();
    }

    @Override
    public void registreObserver(Observer o) {
        // Adicionar true
        boolean statos = true;

        for (Observer ob : observers) {
            if (ob.getClass().equals(o.getClass())) {
                statos = false;
                break;
            }
        }

        // Se manteve-se true vou adicionar
        if (statos) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notifica todos os observadores sobre o estado
     */
    @Override
    public void runAction(Runnable acao) {
        //executorService.execute(acao);
        executorService.execute(acao);

    }

    @Override
    public void notifyObserversStatos(boolean statos) {
        for (int i = 0; i < observers.size(); i++) {
            if (observers.get(i) != null) {
                observers.get(i).actionStatos(statos);
            }
        }
    }

    @Override
    public void notifyObserversDados(GregorianCalendar dataTime) {
        for (int i = 0; i < observers.size(); i++) {
            if (observers.get(i) != null) {
                observers.get(i).actionDados(dataTime);
            }
        }
    }
}
