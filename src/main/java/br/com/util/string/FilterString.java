package br.com.util.string;

/**
 * Filtra os caracteres de uma String.
 *
 * @author Herberts Cruz
 * @version 3.0
 */
public class FilterString implements FormatStringInterface {

    private int maxLenght = 0;
    private String[] typesFilter = {};
    private boolean withSpaces = true;
    private String escapes = "";
    private boolean allowsAccentuation = false;

    /**
     * Pega o tamanho máximo que a String pode ter.
     *
     * @return
     */
    public int getMaxLenght() {
        return maxLenght;
    }

    /**
     * Aplica uma limitação de caracteres para a String resultante do filtro.
     * Por padrão é definido 0(zero), ou seja, infinito.
     *
     * @param maxLenght
     */
    public void setMaxLenght(int maxLenght) {
        this.maxLenght = maxLenght;
    }

    /**
     * Aplica um ou uma série de filtros à String.
     *
     * Os filtros são:
     *
     * number -> permite apenas caracteres numéricos; alpha -> permite apenas
     * caracteres alfabéticos; alnum -> permite apenas caracteres alfanuméricos;
     * tolower -> coloca em minúsculas todos os caracteres; toupper -> coloca em
     * maiúsculas todos os caracteres;
     *
     * Atenção: - O filtro number tem prioridade sobre o filtro alpha que por
     * sua vez tem prioridade sobre o filtro alnum caso sejam aplicados à mesma
     * String. - Se forem aplicados os filtros tolower e toupper na mesma String
     * o último deste dois aplicado terá prioridade. - Se forem aplicados os
     * filtros tolower ou toupper junto com o filtro number, os mesmos não terão
     * efeito.
     *
     * @param typesFilter
     */
    public void setTypesFilter(String... typesFilter) {
        this.typesFilter = typesFilter;
    }

    /**
     * Permite que haja espaços entre os caracteres. O padrão é true, ou seja,
     * permite que tenha espaços entre os valores.
     *
     * @param withSpaces
     */
    public void setWithSpaces(boolean withSpaces) {
        this.withSpaces = withSpaces;
    }

    /**
     * Permite inserir caracteres a serem escapados na String.
     *
     * @param escape
     */
    public void setEscapes(String escapes) {
        this.escapes = escapes;
    }

    /**
     * Defini se é permitido inserir caracteres acentuados. O padrão é false, ou
     * seja, não permite que eles sejam inseridos e os substitui por caracteres
     * equivalentes.
     *
     * @param allowsAccentuation
     */
    public void setAllowsAccentuation(boolean allowsAccentuation) {
        this.allowsAccentuation = allowsAccentuation;
    }

    @Override
    public String apply(String text) {
        //Percorre todos os filtros
        for (String filter : typesFilter) {
            switch (filter.toLowerCase()) {
                //Remove todos os caracteres que não forem numéricos.
                case "number":
                    text = extractNotNumber(text);
                    break;
                //Remove todos os caracteres que não forem alfabéticos.
                case "alpha":
                    text = extractNotAlpha(text);
                    break;
                //Remove todos os caracteres que não forem alfanuméricos.
                case "alnum":
                    text = extractNotAlnum(text);
                    break;
                //Converte o(s) caracter(es) em minúsculas
                case "tolower":
                    text = text.toLowerCase();
                    break;
                //Converte o(s) caracter(es) em maiúsculas
                case "toupper":
                    text = text.toUpperCase();
                    break;
                default:
                    break;
            }
        }
        //Verifica se foi definido um valor máximo de caracteres
        if (maxLenght > 0) {
            //Se a quantidade de caracteres do texto for maior que o limite definido
            //retirna os caracteres sobresalentes, caso contrário retorna o próprio texto
            text = (text.length() > maxLenght) ? text.substring(0, maxLenght) : text;
        }

        return text;
    }

    /**
     * Remove todos os caracteres não numéricos obedecendo as configurações de
     * escape setadas no cabeçalho da classe que lhe couber.
     *
     * @param String text
     * @return String
     */
    private String extractNotNumber(String text) {
        if (text == null) {
            throw new NullPointerException("A string do texto a ser filtrado não pode ser nula.");
        }
        if (escapes == null) {
            throw new NullPointerException("A string de caracteres a serem escapados não pode ser nula.");
        }
        //Adiciona o caracter de espaço
        String spaces = (withSpaces) ? " " : "";
        //Guarda o caracter "]" se houver
        String escapeColchete = "";
        //Se existir um caracter "-" em qualquer posição da string
        if (escapes.matches(".*-.*")) {
            //Retira o caracter "-" de qualquer ponto onde ele estiver
            escapes = escapes.replaceAll("-", "");
            //Adiciona-o ao final para não dar erro na expressão regular
            escapes += "-";
        }
        //Se existir um caracter "]" em qualquer posição da string
        if (escapes.matches(".*].*")) {
            //Retira o caracter "]" de qualquer ponto onde ele estiver
            escapes = escapes.replaceAll("]", "");
            //Preenche a variável vazio com o colchete
            escapeColchete = "]";
        }

        return text.replaceAll("[" + escapeColchete + "^0-9" + spaces + escapes + "]", "");
    }

    /**
     * Remove todos os caracteres não alfabéticos obedecendo as configurações de
     * escape setadas no cabeçalho da classe que lhe couber. Tem o cuidado de
     * substituir os caracteres acentuados por caracteres não acentuados, caso
     * não seja permitido caracteres acentuados.
     *
     * @param String text
     * @return String
     */
    private String extractNotAlpha(String text) {
        if (text == null) {
            throw new NullPointerException("A string do texto a ser filtrado não pode ser nula.");
        }
        if (escapes == null) {
            throw new NullPointerException("A string de caracteres a serem escapados não pode ser nula.");
        }
        //Adiciona os caracteres acentudos
        String accentuation = "";
        //Se permite acentuação
        if (allowsAccentuation) {
            //Adiciona os caracteres acentuados
            accentuation = "À-ÃÈ-ÊÌÍÒ-ÕÙÚà-ãçè-êìíò-õùúçÇ";
        } else {
            //Substitui os caracteres acentuados por caracteres não acentuados
            text = ManipuleString.removerAcentos(text);
        }
        //Adiciona o caracter de espaço
        String spaces = (withSpaces) ? " " : "";

        //Guarda o caracter "]" se houver
        String escapeColchete = "";

        //Se existir um caracter "-" em qualquer posição da string
        if (escapes.matches(".*-.*")) {
            //Retira o caracter "-" de qualquer ponto onde ele estiver
            escapes = escapes.replace("-", "");

            //Adiciona-o ao final para não dar erro na expressão regular
            escapes += "-";
        }
        //Se existir um caracter "]" em qualquer posição da string
        if (escapes.matches(".*].*")) {
            //Retira o caracter "]" de qualquer ponto onde ele estiver
            escapes = escapes.replace("]", "");

            //Preenche a variável vazio com o colchete
            escapeColchete = "]";
        }

        return text.replaceAll("[" + escapeColchete + "^A-Za-z" + spaces + accentuation + escapes + "]", "");
    }

    /**
     * Remove todos os caracteres não alfanuméricos obedecendo as configurações
     * de escape setadas no cabeçalho da classe que lhe couber. Tem o cuidado de
     * substituir os caracteres acentuados por caracteres não acentuados, caso
     * não seja permitido caracteres acentuados.
     *
     * @param String text
     * @return String
     */
    private String extractNotAlnum(String text) {
        if (text == null) {
            throw new NullPointerException("A string do texto a ser filtrado não pode ser nula.");
        }
        if (escapes == null) {
            throw new NullPointerException("A string de caracteres a serem escapados não pode ser nula.");
        }
        //Adiciona os caracteres acentudos
        String accentuation = "";
        //Se permite acentuação
        if (allowsAccentuation) {
            //Adiciona os caracteres acentuados
            accentuation = "À-ÃÈ-ÊÌÍÒ-ÕÙÚà-ãçè-êìíò-õùúçÇ";
        } else {
            //Substitui os caracteres acentuados por caracteres não acentuados
            text = ManipuleString.removerAcentos(text);
        }
        //Adiciona o caracter de espaço
        String spaces = (withSpaces) ? " " : "";
        //Guarda o caracter "]" se houver
        String escapeColchete = "";

        //Se existir um caracter "-" em qualquer posição da string
        if (escapes.matches(".*-.*")) {
            //Retira o caracter "-" de qualquer ponto onde ele estiver
            escapes = escapes.replaceAll("-", "");
            //Adiciona-o ao final para não dar erro na expressão regular
            escapes += "-";
        }
        //Se existir um caracter "]" em qualquer posição da string
        if (escapes.matches(".*].*")) {
            //Retira o caracter "]" de qualquer ponto onde ele estiver
            escapes = escapes.replaceAll("]", "");
            //Preenche a variável vazio com o colchete
            escapeColchete = "]";
        }

        return text.replaceAll("[" + escapeColchete + "^0-9A-Za-z" + spaces + accentuation + escapes + "]", "");
    }
}
