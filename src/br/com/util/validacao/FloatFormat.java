package br.com.util.validacao;

import br.com.util.string.ValidateString;

/**
 *
 * @author Herberts Cruz
 * @author Heverton Cruz
 * @version 1.0
 */
public abstract class FloatFormat {

    /**
     *
     * @param text
     * @return
     */
    public static String numberFloat(String text) {
        String textAux = text.replaceAll("[^0-9]", "");
        String retorno = "";

        if (ValidateString.isNullOrEmpty(textAux) == false) {
            if (ValidateString.isNumber(textAux)) {
                retorno = (Float.parseFloat(textAux) / 100) + "";

                if (retorno.substring(retorno.indexOf(".") + 1).length() == 1) {
                    retorno += "0";
                }
            }
        }

        return retorno.replace(".", ",");
    }

    /**
     *
     * @param text
     * @return
     */
    public static String numberDecimal(String text) {
        String textAux = text.replaceAll("[^0-9]", "");
        String retorno = "";

        if (ValidateString.isNullOrEmpty(textAux) == false) {
            if (ValidateString.isNumber(textAux)) {
                retorno = (Float.parseFloat(textAux) / 1000) + "";

                if (retorno.substring(retorno.indexOf(".") + 1).length() == 1) {
                    retorno += "0";
                }
            }
        }

        return retorno;
    }
}
