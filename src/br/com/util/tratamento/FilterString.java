package br.com.util.tratamento;

/**
 * Cria uma interface para a formatação de texto de objetos TextField.
 *
 * @author Herberts Cruz
 * @version 2.0
 * @deprecated Utilize util.string.FilterString
 */
abstract public class FilterString {

    /**
     * Aplica os filtros na String enviada por parametro.
     *
     * Os valores dos filtros podem ser: - alnum - permite apenas caracteres
     * alfanuméricos A-Z a-z 0-9; - alnumaccent - faz o mesmo que o alnum, mas
     * permiti caracteres acentuados; - number - permite apenas caracteres
     * numéricos; - alpha - permite apenas caracteres alfabéticos; - alphaaccent
     * - faz o mesmo que o alpha, mas permiti caracteres acentuados; - tolower -
     * coloca em minúsculas todos os caracteres; - toupper - coloca em
     * maiúsculas todos os caracteres;
     *
     * @param String[] filters
     * @param String value
     * @return String
     */
    public static String filter(String[] filters, String value) {
        return filter(filters, value, false, "");
    }

    /**
     * Aplica os filtros na String enviada por parametro, possibilitando
     * permitir ou não caracteres de espaço.
     *
     * Os valores dos filtros podem ser: - alnum - permite apenas caracteres
     * alfanuméricos A-Z a-z 0-9; - alnumaccent - faz o mesmo que o alnum, mas
     * permiti caracteres acentuados; - number - permite apenas caracteres
     * numéricos; - alpha - permite apenas caracteres alfabéticos; - alphaaccent
     * - faz o mesmo que o alpha, mas permiti caracteres acentuados; - tolower -
     * coloca em minúsculas todos os caracteres; - toupper - coloca em
     * maiúsculas todos os caracteres;
     *
     * @param String[] filters
     * @param String value
     * @param boolean withSpace
     * @return String
     */
    public static String filter(String[] filters, String value, boolean withSpace) {
        return filter(filters, value, withSpace, "");
    }

    /**
     * Aplica os filtros na String enviada por parametro, possibilitando
     * permitir ou não caracteres de espaço e adicionar caracteres a serem
     * escapados.
     *
     * Os valores dos filtros podem ser: - alnum - permite apenas caracteres
     * alfanuméricos A-Z a-z 0-9; - alnumaccent - faz o mesmo que o alnum, mas
     * permiti caracteres acentuados; - number - permite apenas caracteres
     * numéricos; - alpha - permite apenas caracteres alfabéticos; - alphaaccent
     * - faz o mesmo que o alpha, mas permiti caracteres acentuados; - tolower -
     * coloca em minúsculas todos os caracteres; - toupper - coloca em
     * maiúsculas todos os caracteres;
     *
     * @param String[] filters
     * @param String value
     * @param boolean withSpace
     * @param String escape
     * @return String
     */
    public static String filter(String[] filters, String value, boolean withSpace, String escape) {
        //Percorre todos os filtros
        for (String filter : filters) {
            switch (filter.toLowerCase()) {
                //Remove todos os caracteres que não forem alfanuméricos.
                //Se for setado alguma escape no construtor considera os caracteres escapados.
                case "alnum":
                    //Se foi definido caracteres a serem escapados
                    if (!escape.isEmpty()) {
                        value = extractNotAlnum(value, withSpace, escape);
                    } else {
                        value = extractNotAlnum(value, withSpace);
                    }
                    break;
                //Remove todos os caracteres que não forem alfanuméricos acentuados.
                //Se for setado alguma escape no construtor considera os caracteres escapados.
                case "alnumaccent":
                    if (!escape.isEmpty()) {
                        value = extractNotAlnum(value, withSpace, true, escape);
                    } else {
                        value = extractNotAlnum(value, withSpace, true);
                    }
                    break;
                //Remove todos os caracteres que não forem numéricos.
                //Se for setado alguma escape no construtor considera os caracteres escapados.
                case "number":
                    if (!escape.isEmpty()) {
                        value = extractNotNumber(value, withSpace, escape);
                    } else {
                        value = extractNotNumber(value, withSpace);
                    }
                    break;
                //Remove todos os caracteres que não forem alfabéticos acentuados.
                //Se for setado alguma escape no construtor considera os caracteres escapados.
                case "alpha":
                    if (!escape.isEmpty()) {
                        value = extractNotAlpha(value, withSpace, escape);
                    } else {
                        value = extractNotAlpha(value, withSpace);
                    }
                    break;
                //Remove todos os caracteres que não forem alfabéticos acentuados.
                //Se for setado alguma escape no construtor considera os caracteres escapados.
                case "alphaaccent":
                    if (!escape.isEmpty()) {
                        value = extractNotAlpha(value, withSpace, true, escape);
                    } else {
                        value = extractNotAlpha(value, withSpace, true);
                    }
                    break;
                //Converte o(s) caracter(es) em minúsculas
                case "tolower":
                    value = value.toLowerCase();
                    break;
                //Converte o(s) caracter(es) em maiúsculas
                case "toupper":
                    value = value.toUpperCase();
                    break;
                default:
                    break;
            }
        }

        return value;
    }

    /**
     * Remove todos os caracteres não alfanuméricos e pergunta se o caracter de
     * espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @return String
     */
    private static String extractNotAlnum(String text, boolean withSpace) {
        return extractNotAlnum(text, withSpace, false);
    }

    /**
     * Remove todos os caracteres não alfanuméricos permitindo escapar alguns e
     * pergunta se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param String escape
     * @return String
     */
    private static String extractNotAlnum(String text, boolean withSpace, String escape) {
        return extractNotAlnum(text, withSpace, false, escape);
    }

    /**
     * Remove todos os caracteres não alfanuméricos permitindo letra acentuadas
     * e pergunta se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param boolean withAccentuation
     * @return String
     */
    private static String extractNotAlnum(String text, boolean withSpace, boolean withAccentuation) {
        return extractNotAlnum(text, withSpace, withAccentuation, "");
    }

    /**
     * Remove todos os caracteres não alfanuméricos permitindo letra acentuadas
     * e caracteres escapados, perguntando se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param boolean withAccentuation
     * @param String escape
     * @return String
     */
    private static String extractNotAlnum(String text, boolean withSpace, boolean withAccentuation, String escape) {
        if (text == null) {
            throw new NullPointerException("A string do texto a ser filtrado não pode ser nula.");
        }
        if (escape == null) {
            throw new NullPointerException("A string de caracteres a serem escapados não pode ser nula.");
        }

        //Adiciona os caracteres acentudos
        String accentuation = (withAccentuation) ? "À-ÃÈ-ÊÌÍÒ-ÕÙÚà-ãçè-êìíò-õùúçÇ" : "";
        //Adiciona o caracter de espaço
        String space = (withSpace) ? " " : "";

        //Guarda o caracter "]" se houver
        String escapeColchete = "";

        //Se existir um caracter "-" em qualquer posição da string
        if (escape.matches(".*-.*")) {
            //Retira o caracter "-" de qualquer ponto onde ele estiver
            escape = escape.replaceAll("-", "");

            //Adiciona-o ao final para não dar erro na expressão regular
            escape += "-";
        }
        //Se existir um caracter "]" em qualquer posição da string
        if (escape.matches(".*].*")) {
            //Retira o caracter "]" de qualquer ponto onde ele estiver
            escape = escape.replaceAll("]", "");

            //Preenche a variável vazio com o colchete
            escapeColchete = "]";
        }

        return text.replaceAll("[" + escapeColchete + "^0-9A-Za-z" + space + accentuation + escape + "]", "");
    }

    /**
     * Remove todos os caracteres não numéricos e pergunta se o caracter de
     * espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @return String
     */
    private static String extractNotNumber(String text, boolean withSpace) {
        return extractNotNumber(text, withSpace, "");
    }

    /**
     * Remove todos os caracteres não numéricos permitindo caracteres escapados
     * e pergunta se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param String escape
     * @return String
     */
    private static String extractNotNumber(String text, boolean withSpace, String escape) {
        if (text == null) {
            throw new NullPointerException("A string do texto a ser filtrado não pode ser nula.");
        }
        if (escape == null) {
            throw new NullPointerException("A string de caracteres a serem escapados não pode ser nula.");
        }

        //Adiciona o caracter de espaço
        String space = (withSpace) ? " " : "";

        //Guarda o caracter "]" se houver
        String escapeColchete = "";

        //Se existir um caracter "-" em qualquer posição da string
        if (escape.matches(".*-.*")) {
            //Retira o caracter "-" de qualquer ponto onde ele estiver
            escape = escape.replaceAll("-", "");

            //Adiciona-o ao final para não dar erro na expressão regular
            escape += "-";
        }
        //Se existir um caracter "]" em qualquer posição da string
        if (escape.matches(".*].*")) {
            //Retira o caracter "]" de qualquer ponto onde ele estiver
            escape = escape.replaceAll("]", "");

            //Preenche a variável vazio com o colchete
            escapeColchete = "]";
        }

        return text.replaceAll("[" + escapeColchete + "^0-9" + space + escape + "]", "");
    }

    /**
     * Remove todos os caracteres não alfabéticos e pergunta se o caracter de
     * espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @return String
     */
    private static String extractNotAlpha(String text, boolean withSpace) {
        return extractNotAlpha(text, withSpace, false);
    }

    /**
     * Remove todos os caracteres não alfabéticos permitindo escapar alguns e
     * pergunta se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param String escape
     * @return String
     */
    private static String extractNotAlpha(String text, boolean withSpace, String escape) {
        return extractNotAlpha(text, withSpace, false, escape);
    }

    /**
     * Remove todos os caracteres não alfabéticos permitindo letra acentuadas e
     * pergunta se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param boolean withAccentuation
     * @return String
     */
    private static String extractNotAlpha(String text, boolean withSpace, boolean withAccentuation) {
        return extractNotAlpha(text, withSpace, withAccentuation, "");
    }

    /**
     * Remove todos os caracteres não alfabéticos permitindo letra acentuadas e
     * caracteres escapados, perguntando se o caracter de espaço é necessário.
     *
     * @param String text
     * @param boolean withSpace
     * @param boolean withAccentuation
     * @param String escape
     * @return String
     */
    private static String extractNotAlpha(String text, boolean withSpace, boolean withAccentuation, String escape) {
        if (text == null) {
            throw new NullPointerException("A string do texto a ser filtrado não pode ser nula.");
        }
        if (escape == null) {
            throw new NullPointerException("A string de caracteres a serem escapados não pode ser nula.");
        }

        //Adiciona os caracteres acentudos
        String accentuation = (withAccentuation) ? "À-ÃÈ-ÊÌÍÒ-ÕÙÚà-ãçè-êìíò-õùúçÇ" : "";
        //Adiciona o caracter de espaço
        String space = (withSpace) ? " " : "";

        //Guarda o caracter "]" se houver
        String escapeColchete = "";

        //Se existir um caracter "-" em qualquer posição da string
        if (escape.matches(".*-.*")) {
            //Retira o caracter "-" de qualquer ponto onde ele estiver
            escape = escape.replace("-", "");

            //Adiciona-o ao final para não dar erro na expressão regular
            escape += "-";
        }
        //Se existir um caracter "]" em qualquer posição da string
        if (escape.matches(".*].*")) {
            //Retira o caracter "]" de qualquer ponto onde ele estiver
            escape = escape.replace("]", "");

            //Preenche a variável vazio com o colchete
            escapeColchete = "]";
        }

        return text.replaceAll("[" + escapeColchete + "^A-Za-z" + space + accentuation + escape + "]", "");
    }
}