package com.hsc.observer;

/**
 * Interface genérica para observadores.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public interface Observador {
    /**
     * Recebe um objeto de informações sobre o assunto ao qual se deseja saber.
     * 
     * @param informacoes 
     */
    public void update(Object... informacoes);
}
