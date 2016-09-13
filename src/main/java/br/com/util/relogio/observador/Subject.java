/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio.observador;

import java.util.GregorianCalendar;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public interface Subject 
{
    // Registra observador.
    public void registreObserver(Observer o);
    // Remove um observador.
    public void removeObserver(Observer o);
    // Notifica observadores.
    public void notifyObserversStatos(boolean statos);
    // Notifica observadores.
    public void notifyObserversDados(GregorianCalendar dataTime);
    // Notificar nova thread com ação
    public void runAction(Runnable acao);
}
