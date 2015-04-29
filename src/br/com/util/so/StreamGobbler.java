/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.so;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class StreamGobbler extends Thread {

    private InputStream is;
    private String type;

    public StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            //aqui o readLine bloqueia a thread  
            while ((line = br.readLine()) != null) {
                System.out.println(type + " > " + line);
            }
        } catch (IOException | IllegalThreadStateException e) {
            e.printStackTrace();
        }
    }
}
