package br.com.util.ansii;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Descobre o numero da tecla no padão ansi
 * Link:
 *  http://www.infoisis.eti.br/mqisi/tex/dos/pgtxdos001.htm
 * 
 * @author Heverton Cruz
 * @version 1.1
 */
public class Ansii {

    private HashMap arrayansii = new HashMap();

    /**
     *
     */
    public Ansii() {
        //Letras
        for (int i = 65; i <= 90; i++) {
            arrayansii.put(KeyEvent.getKeyText(i).trim(), i);
        }

        //Número do teclado Left Pad
        for (int i = 106; i <= 123; i++) {
            arrayansii.put(KeyEvent.getKeyText(i).replace("NumPad", "").replace("Teclado Numérico ", "").trim(), i);
        }
    }

    /**
     * O numero da tecla no padrão ansi
     *
     * @param a
     * @return
     */
    public int getArrayAnsii(String a) {
        return Integer.parseInt(arrayansii.get(a) + "");
    }

    /**
     * O numero da tecla no padrão ansi na representação de String
     *
     * @param a
     * @return
     */
    public String getArrayAnsiiString(String a) {
        return arrayansii.get(a) + "";
    }
}
