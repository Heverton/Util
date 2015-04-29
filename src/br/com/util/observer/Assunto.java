package br.com.util.observer;

/**
 * Interface genérica para objetos registradores.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public interface Assunto {
    public void registraObservador(Observador observador);
    public void removeObservador(Observador observador);
    public void notificaObservador(Class observador, Object ... informacoes);
    public void notificaObservadores(Object ... informacoes);
}
