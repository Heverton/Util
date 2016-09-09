/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.validacao;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public abstract class ValidateInt {

    /**
     *
     * @param a
     * @param index
     * @return
     */
    public static int parceInt(Object[] a, int index) {
        try {
            return Integer.parseInt(a[index].toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    /**
     * Converte o objeto selecionado por index para o tipo primitivo int
     * Caso de erro retorna "zero"
     *
     * @param a
     * @param index
     * @return
     */
    public static int parceDoubleInt(Object[] a, int index) {
        try {
            float aux = Float.parseFloat(a[index].toString());

            return (int) aux;
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    /**
     * Usada para caso de campo vazio.
     *
     * @param a
     * @return int
     */
    public static int validaInt(String a) {
        try {
            return Integer.parseInt(a);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Verifica se o valor inserido é um double padrão ##.##
     * Usada para caso de campo vazio.
     *
     * @param a
     * @return Double
     */
    public static double validaDouble(String a) throws NullPointerException {
        try {
            if (a.isEmpty() == true) {
                throw new NullPointerException();
            }

            if (a == null) {
                throw new NullPointerException();
            }

            return Double.parseDouble(a);
        } catch (NumberFormatException | NullPointerException e) {
            return 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Usada para caso de campo vazio.
     *
     * @param a
     * @return int
     * @throws NumberFormatException
     */
    public static int validaIntException(String a) throws NumberFormatException {
        return Integer.parseInt(a);
    }

    /**
     *
     * @param a
     * @return
     */
    public static int parceInt(Object a) {
        try {
            return Integer.parseInt(a.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     *
     * @param a
     * @param index
     * @return
     */
    public static String parceString(Object[] a, int index) {
        try {
            if (a == null || a[index].toString().trim().isEmpty()) {
                throw new NullPointerException("");
            }

            return a[index].toString();
        } catch (NumberFormatException | NullPointerException e) {
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     *
     * @param a
     * @return
     */
    public static String parceString(Object a) {
        try {
            if (a == null || a.toString().trim().isEmpty()) {
                throw new NullPointerException("");
            }

            return a.toString();
        } catch (NumberFormatException | NullPointerException e) {
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }
}
