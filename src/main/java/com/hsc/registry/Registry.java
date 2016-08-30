package com.hsc.registry;

import java.util.HashMap;

/**
 * Registry = Implementa o Design Pattern Registry.
 *
 * @author Herberts Cruz
 * @version 1.0
 */
public class Registry {

    protected HashMap registry = null;
    protected static Registry instance = null;

    private Registry() {
        registry = new HashMap();
    }

    /**
     * Registra um valor com sua chave de identificação
     *
     * @param Object key
     * @param Object value
     * @return void
     */
    public static void set(Object key, Object value) {
        if (instance == null) {
            instance = new Registry();
        }
        instance.registry.put(key, value);
    }

    /**
     * Resgata um valor gravado por sua chave.
     *
     * @param Object key
     * @return Object
     */
    public static Object get(Object key) {
        if (instance == null) {
            instance = new Registry();
        }
        return instance.registry.get(key);
    }

    public static String toStringRegistry() {
        return instance.registry.toString();
    }
}