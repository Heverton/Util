package com.hsc.validacao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Valida uma String
 *
 * @author Herberts Cruz, Heverton Cruz, Helder Cruz
 * @version 2.0
 * @deprecated Utilize util.string.ValidateString
 */
public abstract class ValidateString {

    /**
     * Verifica se o número possui o número de casas decimais que se deseja.
     *
     * @param amount
     * @return boolean
     */
    public static boolean hasNumberDecimalPlaces(String number, int amount) {
        try {
            String[] n = number.split("[,]");
            
            return (n[1].trim().length() == amount);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }
    
    /**
     * Verifica se possui a quantidade de caracteres desejada, filtra string nula.
     *
     * @param text
     * @param amount
     * @return boolean
     */
    public static boolean isLength(String text, int amount) {
        return (text == null) ? false : (text.length() == amount);
    }

    /**
     * Verifica se é uma string nula ou vazia, aplica um trim() na string para 
     * garantir que não foi preenchida com espaços.
     *
     * @param text
     * @return boolean
     */
    public static boolean isNullOrEmpty(String text) {
        return (text == null) ? true : (text.trim().isEmpty());
    }

    /**
     * Verifica se a string é composta apenas de caracteres números, também filtra 
     * valores nulos.
     *
     * @param text
     * @return boolean
     */
    public static boolean isNumber(String text) {
        return isNumber(text, false);
    }

    /**
     * Verifica se a string é composta apenas de caracteres números de ponto 
     * flutuante.
     *
     * @param text
     * @param floatPoint
     * @return boolean
     */
    public static boolean isNumber(String text, boolean floatPoint) {
        return isNumber(text, floatPoint, false);
    }

    /**
     * Verifica se a string é composta apenas de caracteres números de ponto flutuante, 
     * permitindo considerar a vírgula como ponto flutuante.
     *
     * @param text
     * @param floatPoint
     * @param acceptsComma
     * @return boolean
     */
    public static boolean isNumber(String text, boolean floatPoint, boolean acceptsComma) {
        String regexFloatPoint = (acceptsComma) ? "[.,]" : "[.]";
        String regex = (floatPoint) ? "[0-9]+" + regexFloatPoint + "[0-9]+" : "[0-9]+";
        return executeRegex(text, regex);
    }

    /**
     * Verifica se a string é composta apenas de caracteres alfabéticos.
     *
     * @param text
     * @return boolean
     */
    public static boolean isAlpha(String text) {
        return isAlpha(text, false);
    }

    /**
     * Verifica se a string é composta apenas de caracteres alfabéticos, permitindo 
     * considerar os espaços na verificação.
     *
     * @param text
     * @param withSpaces
     * @return boolean
     */
    public static boolean isAlpha(String text, boolean withSpaces) {
        return isAlpha(text, withSpaces, false);
    }

    /**
     * Verifica se a string é composta apenas de caracteres alfabéticos, permitindo 
     * considerar os espaços na verificação e considerando os caracteres especiais.
     *
     * @param text
     * @param withSpaces
     * @param withSpecialCharacters
     * @return boolean
     */
    public static boolean isAlpha(String text, boolean withSpaces, boolean withSpecialCharacters) {
        String txWithSpaces = (withSpaces) ? " " : "";
        String txWithSpecialCharacters = (withSpecialCharacters) ? "À-ÃÈ-ÊÌÍÒ-ÕÙÚà-ãçè-êìíò-õùú" : "";
        return executeRegex(text, "[A-Za-z"+ txWithSpecialCharacters + txWithSpaces + "]+");
    }

    /**
     * Verifica se a string é composta apenas de caracteres alfanuméricos.
     *
     * @param text
     * @return boolean
     */
    public static boolean isAlnum(String text) {
        return isAlnum(text, false);
    }

    /**
     * Verifica se a string é composta apenas de caracteres alfabéticos, permitindo 
     * considerar os espaços na verificação.
     *
     * @param text
     * @param withSpaces
     * @return boolean
     */
    public static boolean isAlnum(String text, boolean withSpaces) {
        return isAlnum(text, withSpaces, false);
    }
    
    /**
     * Verifica se a string é composta apenas de caracteres alfabéticos, permitindo 
     * considerar os espaços na verificação e considerando os caracteres especiais.
     *
     * @param text
     * @param withSpaces
     * @param withSpecialCharacters
     * @return boolean
     */
    public static boolean isAlnum(String text, boolean withSpaces, boolean withSpecialCharacters) {
        String txWithSpaces = (withSpaces) ? " " : "";
        String txWithSpecialCharacters = (withSpecialCharacters) ? "À-ÃÈ-ÊÌÍÒ-ÕÙÚà-ãçè-êìíò-õùú" : "";
        return executeRegex(text, "[0-9A-Za-z"+ txWithSpecialCharacters + txWithSpaces + "]+");
    }

    /**
     * Verifica se o e-mail é válido.
     *
     * @param  email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return executeRegex(email, "[A-Za-z0-9_.-]+@([A-Za-z0-9_.-]+.)+[A-Za-z]{2,4}");
    }

    /**
     * Verifica se a url de site é válida.
     *
     * @param  site
     * @return boolean
     */
    public static boolean isSite(String site) {
        return executeRegex(site, "(http://)?(www.)?([A-Za-z0-9_.-]+.)+[A-Za-z]{2,4}");
    }

    /**
     * Verifica se o IP é válido.
     *
     * @param  ip
     * @return boolean
     */
    public static boolean isIP(String ip) {
        return executeRegex(ip, "([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])");
    }

    /**
     * Verifica se o cpf é válido.
     *
     * @param  cpf
     * @return boolean
     */
    public static boolean isCpf(String cpf) {
        //Retira qualquer caracter que não for numérico
        cpf = cpf.replaceAll("[^0-9]", "");

        //Se existirem 11 caracteres e se estes não são números repetidos
        if (cpf.length() == 11 && !cpf.matches("0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|8{11}|9{11}")) {
            int dCpf;
            int dVerif1 = 0, dVerif2 = 0;

            for (int nCount = 1; nCount < 11; nCount++) {
                //Pega o dígito do CPF
                dCpf = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

                //Se estiver na posição do anterior ao primeiro dígito verificador
                if (nCount < 10) {
                    //Multiplica o primeiro dígito por 10, o segundo por 9, ....
                    dVerif1 += ((11 - nCount) * dCpf);
                } else {
                    //Faz o cálculo para gerar o primeiro dígito verificador válido
                    dVerif1 = (dVerif1 % 11 < 2) ? 0 : 11 - (dVerif1 % 11);
                }

                //Se estiver na posição do primeiro dígito verificador
                if (nCount == 10) {
                    //Multiplica pelo primeiro dígito verificador encontrado
                    dVerif2 += (2 * dVerif1);

                    //Faz o cálculo para gerar o segundo dígito verificador válido
                    dVerif2 = (dVerif2 % 11 < 2) ? 0 : 11 - (dVerif2 % 11);
                } else {
                    //Multiplica o primeiro dígito por 11, o segundo por 10, ....
                    dVerif2 += ((12 - nCount) * dCpf);
                }
            }

            //Digito verificador informado
            String dsVerifInformado = cpf.substring(cpf.length() - 2, cpf.length());
            //Digito verificador gerado
            String dsVerifGerado = String.valueOf(dVerif1) + String.valueOf(dVerif2);

            //Se o verificador informado for igual ao gerado
            return dsVerifInformado.equals(dsVerifGerado);
        } else {
            return false;
        }
    }

    /**
     * Verifica se o cnpj é válido.
     *
     * @param  cnpj
     * @return boolean
     */
    public static boolean isCnpj(String cnpj) {
        //Retira qualquer caracter que não for numérico
        cnpj = cnpj.replaceAll("[^0-9]", "");

        //Se existirem 11 caracteres e se estes não são números repetidos
        if (cnpj.length() == 14 && !cnpj.matches("0{14}")) {
            int dCnpj;
            int dVerif1 = 0, dVerif2 = 0;

            for (int nCount = 1; nCount < 6; nCount++) {
                //Pega o dígito do CPF
                dCnpj = Integer.valueOf(cnpj.substring(nCount - 1, nCount)).intValue();

                //Se estiver na posição do anterior ao primeiro dígito verificador
                if (nCount < 5) {
                    //Multiplica o primeiro dígito por 5, o segundo por 4, ....
                    dVerif1 += ((6 - nCount) * dCnpj);
                } else {
                    //Multiplica o quinto dígito pelo fator 9
                    dVerif1 += (9 * dCnpj);
                }

                //Multiplica o primeiro dígito por 6, o segundo por 7, ....
                dVerif2 += ((7 - nCount) * dCnpj);
            }

            for (int nCount = 6; nCount < 14; nCount++) {
                //Pega o dígito do CPF
                dCnpj = Integer.valueOf(cnpj.substring(nCount - 1, nCount)).intValue();

                //Se estiver na posição do anterior ao primeiro dígito verificador
                if (nCount < 13) {
                    //Multiplica o primeiro dígito por 8, o segundo por 7, ....
                    dVerif1 += ((14 - nCount) * dCnpj);
                } else {
                    //Faz o cálculo para gerar o primeiro dígito verificador válido
                    dVerif1 = (dVerif1 % 11 < 2) ? 0 : 11 - (dVerif1 % 11);
                }

                //Se estiver na posição do primeiro dígito verificador
                if (nCount == 13) {
                    //Multiplica pelo primeiro dígito verificador encontrado
                    dVerif2 += (2 * dVerif1);

                    //Faz o cálculo para gerar o segundo dígito verificador válido
                    dVerif2 = (dVerif2 % 11 < 2) ? 0 : 11 - (dVerif2 % 11);
                } else {
                    //Multiplica o primeiro dígito por 9, o segundo por 8, ....
                    dVerif2 += ((15 - nCount) * dCnpj);
                }
            }

            //Digito verificador informado
            String dsVerifInformado = cnpj.substring(cnpj.length() - 2, cnpj.length());
            //Digito verificador gerado
            String dsVerifGerado = String.valueOf(dVerif1) + String.valueOf(dVerif2);

            //Se o verificador informado for igual ao gerado
            return dsVerifInformado.equals(dsVerifGerado);
        } else {
            return false;
        }
    }

    /**
     * Verifica se o texto enviado é nulo e verifica o dados de acordo com a regex enviada.
     *
     * @return boolean
     */
    private static boolean executeRegex(String text, String regex) {
        return (text.isEmpty()) ? false : text.matches(regex);
    }

    /**
     *
     * @return String
     */
    public static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }

            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    /**
     *
     * @param  frase
     * @param  algoritmo
     * @return byte[]
     */
    public static byte[] gerarHash(String frase, String algoritmo) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Inteiro - false; Fracionado - true;
     *
     * @param valor
     * @return boolean
     * @deprecated Utilize isNumber(valor, true, true)
     */
    public static boolean inteiroFracionado(String valor) {
        if (valor.matches(".*[,.].*")) {
            // Fracionado.
            return true;
        } else {
            // Inteiro.
            return false;
        }
    }

    /**
     * Aceita números e vírgula.
     *
     * @param text
     * @return boolean
     */
    public static boolean numberMonetario(String text) {
        if (!text.matches("[0-9]|[,]")) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Permite ou não o numero de casas decimais Permite - true Não Permite -
     * false
     *
     * @param numero
     * @param numerocasas
     * @return
     * @deprecated Utilize hasNumberDecimalPlaces(numero, numerocasas)
     */
    public static boolean inteiro(String numero, int numerocasas) {
        String[] b = numero.split("[,]");

        try {
            if (b[1].length() == numerocasas) {
                // Número de casa permitido
                return true;
            } else {
                // Número de casa não permitido 
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Número de casa não permitido 
            return false;
        }
    }

    /**
     * Verifica se todos os caracteres são numéricos
     *
     * @param  text
     * @return boolean
     * @deprecated Utilize isNumber
     */
    public static boolean number(String text) {
        if (!text.matches("[0-9]*")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifica se todos os caracteres são alfanuméricos.
     *
     * @param  text
     * @return boolean
     * @deprecated Utilize isAlnum
     */
    public static boolean alnum(String text) {
        if (!text.matches("[0-9A-Za-z]*")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifica se todos os caracteres são alfabéticos.
     *
     * @param  text
     * @return boolean
     * @deprecated Utilize isAlpha
     */
    public static boolean alpha(String text) {
        if (!text.matches("[A-Za-z]*")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifica se possui a quantidade de caracteres desejada.
     *
     * @param  text
     * @param  quantidade
     * @return boolean
     * @deprecated Utilize isLenght
     */
    public static boolean length(String text, int quantidade) {
        if (text.length() == quantidade) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifica se todos os caracteres obedecem à expressão regular enviada como
     * parametro.
     *
     * @param text
     * @param regex = não colocar início e fim da regex (^$)
     * @return boolean
     * @deprecated Utilize a função nativa matches para Strings
     */
    public static boolean regex(String text, String regex) {
        if (!text.matches(regex)) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Retira todas as tags HTML de um código HTML, devolvendo o texto restante.
     *
     * @param  mensagem que terá as tags retiradas.
     * @return texto sem tags HTML.
     * @deprecated Utilize ManipuleString.limpaHTML(html)
     */
    public static String limpaHTML(String mensagem) {
        return mensagem.replaceAll("\\<.*?\\>", "");
    }

    /**
     * Retira todas as tags HTML de um código HTML, devolvendo o texto restante.
     *
     * Caso vazio e sem HTML = false Não vazio e sem HTML = true
     *
     * @param  mensagem que terá as tags retiradas.
     * @return boolean caso vazio true.
     * @deprecated Utilize a combinação isNullOrEmpty(ManipuleString.limpaHTML(html))
     */
    public static boolean limpaHTMLBoolean(String mensagem) throws NullPointerException {
        if (mensagem.replaceAll("\\<.*?\\>", "").trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
