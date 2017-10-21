/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.relogio;

import br.com.util.relogio.main.Relogio;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author Heverton
 * @version 1.0
 */
public class Teste {

    @Test
    public void start() {
        Relogio rel = new Relogio();

        rel.acertarPonteros(GregorianCalendar.getInstance().get(GregorianCalendar.YEAR),
                GregorianCalendar.getInstance().get(GregorianCalendar.MONDAY),
                GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH),
                GregorianCalendar.getInstance().get(GregorianCalendar.HOUR_OF_DAY),
                GregorianCalendar.getInstance().get(GregorianCalendar.MINUTE),
                GregorianCalendar.getInstance().get(GregorianCalendar.SECOND));
        rel.start();

        SimpleDateFormat formtVisual = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        System.out.println("--- " + formtVisual.format(rel.getDataHora().getTime()));

        rel.stop();

        rel.acertarPonteros(GregorianCalendar.getInstance().get(GregorianCalendar.YEAR),
                GregorianCalendar.getInstance().get(GregorianCalendar.MONDAY),
                GregorianCalendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH),
                GregorianCalendar.getInstance().get(GregorianCalendar.HOUR_OF_DAY) + 2,
                GregorianCalendar.getInstance().get(GregorianCalendar.MINUTE) + 1,
                GregorianCalendar.getInstance().get(GregorianCalendar.SECOND) + 1);
        rel.restart();

        System.out.println("--- " + formtVisual.format(rel.getDataHora().getTime()));

//        while (true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//            }
//
//            System.out.println("--- " + formtVisual.format(rel.getDataHora().getTime()));
//        }
    }
}
