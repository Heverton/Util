package br.com.util.observer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observer;

/**
 * Informa o observador sobre o assunto.
 *
 * @author Herberts Cruz
 * @version 1.1
 */
public class Informante implements Assunto {

    /**
     * Armazena os observadores deste assunto.
     */
    private Map<Class, Observador> observadores = new HashMap();
    private static Informante instance = null;

    private Informante() {
    }

    public synchronized static Informante getInstance() {
        if (instance == null) {
            instance = new Informante();
        }

        return instance;
    }

    /**
     * Não deve ser utilizado a mesma classe ou a mesma instancia para o observador na inserção
     * 
     * @param observador 
     */
    @Override
    public void registraObservador(Observador observador) {
        //Registra apenas se nunca tiver sido registrado anteriormente
        if (!observadores.containsKey(observador.getClass())) {
            observadores.put(observador.getClass(), observador);
        }
    }

    @Override
    public void removeObservador(Observador observador) {
        //Executa a remoção apenas se foi registrado anteriomente
        if (observadores.containsKey(observador.getClass())) {
            observadores.remove(observador.getClass());
        }
    }

    @Override
    public void notificaObservador(Class observador, Object... informacoes) {
        if (observadores.containsKey(observador)) {
            observadores.get(observador).update(informacoes);
        }
    }

    @Override
    public void notificaObservadores(Object... informacoes) {
        Iterator<Observador> observadoresi = observadores.values().iterator();

        while (observadoresi.hasNext()) {
            observadoresi.next().update(informacoes);
        }
    }
}
