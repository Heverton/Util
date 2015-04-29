package br.com.util.validacao;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public abstract class IntFormat {

    /**
     * Usado apenas em EAN com 13 dígitos. Controu um numero com zeros a
     * esquerda sem o digito verificador
     *
     * @param text
     * @param qtdNumeros
     * @return String
     * @throws NumberFormatException
     * @deprecated Utilize ManipuleString.lpad(text, "0", qtdNumeros)
     */
    public static String numberIntZeroEsquerda(String text, int qtdNumeros) throws NumberFormatException {
        int qtd = text.length();
        String resultado = "";

        if (qtd != 8) {
            if (qtd > 13) {
                throw new NumberFormatException("Número muito grande para ser usado :" + qtd);
            } else if (qtd < qtdNumeros) {
                for (int i = 0; i < (qtdNumeros - qtd); i++) {
                    resultado += "0";
                }

                return resultado + text;
            } else {
                return text;
            }
        } else {
            return text;
        }
    }

    /**
     * Preenche as casa decimais
     *
     * @param text
     * @param numeroCasasDecimais
     * @return
     * @throws NumberFormatException
     * @throws IndexOutOfBoundsException
     */
    public static String numberFloatZeroDireita(String text, int numeroCasasDecimais) throws NumberFormatException, IndexOutOfBoundsException {
        String[] str = text.split("[,.]");
        String resultado = "";

        try {
            for (int i = str[1].length(); i < 3; i++) {
                resultado += "0";
            }
            return text + resultado;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Não é um número com casas decimais :" + e.getMessage());
        }
    }
}
