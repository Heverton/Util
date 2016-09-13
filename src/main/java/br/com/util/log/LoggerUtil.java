/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.log;

import org.apache.log4j.Logger;

/**
 *
 * @author Heverton
 * @version 1.0
 */
public class LoggerUtil {

    private Logger logger;

    public LoggerUtil(Logger logger) {
        this.logger = logger;
    }

    public synchronized void info(Object message) {
        logger.info(message);
    }

    public synchronized void info(Object message, Throwable t) {
        logger.info(message, t);
    }

    public synchronized void warn(Object message) {
        logger.warn(message);
    }

    public synchronized void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }

    public synchronized void debug(Object message) {
        logger.debug(message);
    }

    public synchronized void debug(Object message, Throwable t) {
        logger.debug(message, t);
    }

    public synchronized void error(Object message) {
        logger.error(message);
    }

    public synchronized void error(Object message, Throwable t) {
        logger.error(message, t);
    }

    public synchronized void fatal(Object message) {
        logger.fatal(message);
    }

    public synchronized void fatal(Object message, Throwable t) {
        logger.fatal(message, t);
    }

    public synchronized void trace(Object message) {
        logger.trace(message);
    }

    public synchronized void trace(Object message, Throwable t) {
        logger.trace(message, t);
    }

    public synchronized Logger getLogger() {
        return logger;
    }
}
