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
public interface Observer {

    public void actionStatos(boolean statos);

    public void actionDados(GregorianCalendar dataTime);
}
