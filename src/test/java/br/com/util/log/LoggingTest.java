/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.log;

import org.junit.Test;

/**
 * utilizada para realizar Logger
 *
 * @author Heverton
 * @version 1.0
 */
public class LoggingTest {

    @Test
    public void teste() {
        LoggingTest t = new LoggingTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // pausa de 10 segundos
                        Thread.sleep(1);
                        Integer.parseInt("12q");
                    } catch (NumberFormatException | InterruptedException e) {
                        Log4jHost.getInstance(LoggingTest.class).error(e, e);
                    }
                }
            }
        }).start();
    }
}
