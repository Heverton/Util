package br.com.util.ansii;

import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Descobre o numero da tecla no padrão ansi
 * Link:
 *  http://www.infoisis.eti.br/mqisi/tex/dos/pgtxdos001.htm
 *
 * @author Heverton Cruz
 * @author Herberts Cruz
 * @version 1.1
 *
 */
public class Ansii<T> {

    private HashMap<T, Integer> arrayansii = new HashMap();

    /**
     *
     */
    public Ansii() {
        //Letras
        for (int i = 65; i <= 90; i++) {
            arrayansii.put((T)KeyEvent.getKeyText(i).trim(), i);
        }

        //Número do teclado Left Pad
        for (int i = 106; i <= 123; i++) {
            arrayansii.put((T) KeyEvent.getKeyText(i).replace("NumPad", "").replace("Teclado Numérico ", "").trim(), i);
        }
    }

    /**
     * O número da tecla no padrão ansi
     *
     * @param tecla
     * @return
     */

    public T getArrayAnsii(T tecla) {
        return (T) arrayansii.get(tecla);
    }

    public String getArrayAnsiiStr(T tecla) {
        for(T key : arrayansii.keySet()) {
            Integer value = (Integer) tecla;
            if (value == arrayansii.get(key)) {
                return arrayansii.get(key)+"";
            }
        }

        return null;
    }

    /**
     * O numero da tecla no padrão ansi na representação de String
     *
     * @param tecla
     * @deprecated Utiliza a metodo "getArrayAnsii" para int ou string
     */
    public String getArrayAnsiiString(String tecla) {
        return arrayansii.get(tecla) + "";
    }
}
