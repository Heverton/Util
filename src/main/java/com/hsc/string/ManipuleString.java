package com.hsc.string;

import java.text.Normalizer;

/**
 * Classe com métodos de manipulação de String.
 *
 * @author Herberts Cruz
 * @version 1.3
 */
final public class ManipuleString {

    /**
     * Método recursivo de acréscimo de quebra de linha em uma String.
     *
     * @param String text
     * @param int countCaracter
     * @return String
     */
    public static String addQuebraLinha(String text, int countCaracter) {
        //Verifica se a quantidade de caracteres é suficiente e se 
        //existe algum espaço para se pegar o índice
        if (text.length() > countCaracter && text.indexOf(" ") != -1) {
            //Paga a posição do espaço na String
            int quebra = text.indexOf(" ", countCaracter);
            //Se realmente existir um espaço
            if (quebra != -1) {
                //Pega tudo antes do espaço
                String prefix = text.substring(0, quebra);
                //Pega tudo depois do espaço
                String sufix = text.substring(quebra);
                //Retorna a String formada navegando ainda no sufixo recursivamente
                return prefix + "\n" + addQuebraLinha(sufix, countCaracter);
            } else {
                return text;
            }
        } else {
            return text;
        }
    }

    /**
     * Substitui os caracteres especiais por caracteres normais.
     *
     * @param String acentuada
     * @return String
     */
    public static String removerAcentos(String acentuada) {
        CharSequence cs = new StringBuilder(acentuada);
        return Normalizer.normalize(cs, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    
    /**
     * Transforma a string para o formato CamelCase. A String pode ser separada
     * por "-" ou "_" caso se deseje manipular as palavra que sofreram a
     * modificação.
     *
     * @param string
     * @return String
     */
    public static String toCamelCase(String string) {
        return toCamelCase(string, false);
    }

    /**
     * Transforma a string para o formato CamelCase. A String pode ser separada
     * por "-" ou "_" caso se deseje manipular as palavra que sofreram a
     * modificação. Dá a opção de definir que quer que seja aplicado apenas à 
     * primeira letra.
     *
     * @param string
     * @param onlyFirstLetter
     * @return String
     */
    public static String toCamelCase(String string, boolean onlyFirstLetter) {
        String[] strs = null;

        if (string.matches(".*-.*")) {
            strs = string.split("-");
        } else if (string.matches(".*_.*")) {
            strs = string.split("_");
        }

        if (strs != null) {
            String strResult = "";

            for (String str : strs) {
                strResult += toCamelCase(str, onlyFirstLetter);
            }

            string = strResult;
        } else {
            string = string.substring(0, 1).toUpperCase() 
                    + ((onlyFirstLetter) 
                        ? string.substring(1) 
                        : string.substring(1).toLowerCase());
        }

        return string;
    }

    /**
     * Adiciona um caracter específico à esquerda.
     *
     * @param valueToPad
     * @param filler
     * @param size
     * @return String
     */
    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    /**
     * Adiciona um caracter específico à esquerda.
     *
     * @param valueToPad
     * @param filler
     * @param size
     * @return String
     */
    public static String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }
    
    /**
     * Adiciona ao cnpj enviado por parametro a máscara de CNPJ.
     * 
     * @param cnpj
     * @return String
     * @deprecated Use o método MaskString.apply("##.###.###/####-##")
     */
    public static String addMascaraCnpj(String cnpj) {
        //Revome todos os caracteres diferentes de número
        cnpj = cnpj.replaceAll("[^0-9]", "");
        //
        StringBuilder newCnpj = new StringBuilder();
        //Se tiver 14 dígitos
        if(cnpj.length() == 14) {
            newCnpj.append(cnpj.subSequence(0, 2));
            newCnpj.append(".");
            newCnpj.append(cnpj.subSequence(2, 5));
            newCnpj.append(".");
            newCnpj.append(cnpj.subSequence(5, 8));
            newCnpj.append("/");
            newCnpj.append(cnpj.subSequence(8, 12));
            newCnpj.append("-");
            newCnpj.append(cnpj.subSequence(12, 14));
        } else {
            throw new IndexOutOfBoundsException("O cnpj deve conter 14 dígitos numéricos.");
        }
        
        return newCnpj.toString();
    }
    
    /**
     * Adiciona ao cpf enviado por parametro a máscara de CPF.
     * 
     * @param cpf
     * @return String
     * @deprecated Use o método MaskString.apply("###.###.###-##")
     */
    public static String addMascaraCpf(String cpf) {
        //Revome todos os caracteres diferentes de número
        cpf = cpf.replaceAll("[^0-9]", "");
        //
        StringBuilder newCpf = new StringBuilder();
        //Se tiver 11 dígitos
        if(cpf.length() == 11) {
            newCpf.append(cpf.subSequence(0, 3));
            newCpf.append(".");
            newCpf.append(cpf.subSequence(3, 6));
            newCpf.append(".");
            newCpf.append(cpf.subSequence(6, 9));
            newCpf.append("-");
            newCpf.append(cpf.subSequence(9, 11));
        } else {
            throw new IndexOutOfBoundsException("O cpf deve conter 11 dígitos numéricos.");
        }
        
        return newCpf.toString();
    }
    
    /**
     * Adiciona ao telefone enviado por parametro a máscara de Telefone.
     * 
     * @param telefone
     * @return String
     * @deprecated Use o método MaskString.apply("(##) ####-####")
     */
    public static String addMascaraTelefone(String ddd, String telefone) {
        //Revome todos os caracteres diferentes de número
        telefone = telefone.replaceAll("[^0-9]", "");
        //
        StringBuilder newTelefone = new StringBuilder();
        //Se não for nulo ou vazio
        if(!ValidateString.isNullOrEmpty(ddd)) {
            newTelefone.append("(");
            newTelefone.append(ddd);
            newTelefone.append(") ");
        }
        //Se tiver 8 dígitos
        if(telefone.length() == 8) {   
            newTelefone.append(telefone.subSequence(0, 4));
            newTelefone.append("-");
            newTelefone.append(telefone.subSequence(4, 8));
        } else if(telefone.length() == 9) { 
            newTelefone.append(telefone.subSequence(0, 5));
            newTelefone.append("-");
            newTelefone.append(telefone.subSequence(5, 9));
        } else if(telefone.length() == 11) { 
            newTelefone.append(telefone.subSequence(0, 4));
            newTelefone.append(" ");
            newTelefone.append(telefone.subSequence(4, 7));
            newTelefone.append(" ");
            newTelefone.append(telefone.subSequence(7, 11));
        } else {
            throw new IndexOutOfBoundsException("O telefone deve conter 8,9 ou 11 dígitos numéricos.");
        }
        
        return newTelefone.toString();
    }
    
    /**
     * Retira todas as tags HTML de um código HTML, devolvendo o texto restante.
     *
     * @param html
     * @return texto sem tags HTML.
     */
    public static String limpaHTML(String html) {
        return html.replaceAll("\\<.*?\\>", "");
    }
}
