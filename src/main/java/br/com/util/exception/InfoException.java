/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.exception;

/**
 *
 * @author Heverton
 * @version 1.0
 */
public class InfoException extends Exception {

    /**
     * Creates a new instance of
     * <code>InfoException</code> without detail message.
     */
    public InfoException() {
    }

    /**
     * Constructs an instance of
     * <code>InfoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InfoException(String msg) {
        super(msg);
    }
}
