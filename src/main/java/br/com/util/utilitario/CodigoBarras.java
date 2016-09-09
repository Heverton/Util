package br.com.util.utilitario;

import br.com.util.data.DataHora;
import br.com.util.exception.ChaveNFe2Exception;
import br.com.util.string.ManipuleString;

import java.util.Date;

import br.com.util.string.ValidateString;

/**
 * Classe manipuladora de códigos de barras.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public abstract class CodigoBarras {

    /**
     * Enum de constantes com máscaras para tipos de códigos de barras.
     */
    public enum Tipo {
        EAN8(8), EAN10(10), UPCA(12), EAN13(13), GSI_EAN14(14);
        
        private int ean;

        private Tipo(int ean) {
            this.ean = ean;
        }

        public int getQuantidade() {
            return ean;
        }
    }
    
    /**
     * Verifica se o código de barras é válido, espera .
     *
     * @param barCode
     * @return boolean
     */
    public static boolean isBarCodeEAN(String barCode) {
        return isBarCodeEAN(barCode, Tipo.EAN13);
    }

    /**
     * Verifica se o código de barras do tipo EAN enviado é válido.
     *
     * @param barCode
     * @return boolean
     */
    public static boolean isBarCodeEAN(String barCode, Tipo ean) {
        //Verifica se tem caracteres não numéricos
        if (barCode.matches("[^0-9]+")) {
            return false;
        }
        //Verifica se o códigos de barras é composto de números repetidos
        if (barCode.matches("[0]{" + ean.getQuantidade() + "}|[1]{" + ean.getQuantidade() + "}|"
                + "[2]{" + ean.getQuantidade() + "}|[3]{" + ean.getQuantidade() + "}|"
                + "[4]{" + ean.getQuantidade() + "}|[5]{" + ean.getQuantidade() + "}|"
                + "[6]{" + ean.getQuantidade() + "}|[7]{" + ean.getQuantidade() + "}|"
                + "[8]{" + ean.getQuantidade() + "}|[9]{" + ean.getQuantidade() + "}")) {
            return false;
        }
        //Adiciona zeros a esquerda
        barCode = ManipuleString.lpad(barCode, "0", ean.getQuantidade());
        //Retira o dígito verificador
        String barCodeSemVerificador = retiraDigitoVerificador(removeCaracteres(barCode), ean);
        //Verifica se o código sem dígito verificador mais o gerado é igual ao enviado por parametro
        return (barCodeSemVerificador + getVerificador(barCodeSemVerificador)).equals(barCode);
    }
    
    /**
     * Gera um código de barras válido do tipo EAN 13.
     *
     * @param barCodeSemVerificador
     * @return String
     */
    public static String geraBarCodeEAN(String barCodeSemVerificador) {
        return geraBarCodeEAN(barCodeSemVerificador, Tipo.EAN13);
    }
    
    /**
     * Gera um código de barras válido do tipo desejado.
     *
     * @param barCodeSemVerificador
     * @param ean
     * @return String
     */
    public static String geraBarCodeEAN(String barCodeSemVerificador, Tipo ean) {
        //Retira o dígito verificador
        barCodeSemVerificador = removeCaracteres(barCodeSemVerificador);
        //Se a quantidade de dígitos for maior que a desejada menos o dígito verificador
        if(barCodeSemVerificador.length() > (ean.getQuantidade() - 1)) {
            throw new RuntimeException("A String do código de barras tem mais dígitos que o esperado.");
        }
        //Adiciona zeros a esquerda
        String barCode = ManipuleString.lpad((barCodeSemVerificador + getVerificador(barCodeSemVerificador)),
                "0", ean.getQuantidade());
        //Verifica se o código sem dígito verificador mais o gerado é igual ao enviado por parametro
        return barCode;
    }
    
    /**
     * Gera o dígito verificador do tipo de código de barras solicitado.
     *
     * @param barCodeSemVerificador
     * @return String
     */
    private static String getVerificador(String barCodeSemVerificador) {
        //Retira qualquer caracter que não for numérico
        barCodeSemVerificador = removeCaracteres(barCodeSemVerificador);

        int posicaoImpar = 0;
        int posicaoPar = 0;

        for (int i = 0; i < barCodeSemVerificador.length(); i++) {
            Integer posicaoArray = Integer.parseInt(barCodeSemVerificador.charAt(i) + "");
            //Se for par
            if ((i % 2) == 0) {
                posicaoImpar += posicaoArray;
            } else {
                posicaoPar += posicaoArray;
            }
        }
        //Variável que guarda a soma das etapas
        int somaEtapas;
        //Se a quantidade de dígitos sem o verificador
        if ((barCodeSemVerificador.length() % 2) == 0) {
            //Multiplicação da etapa 1 por 3
            int multPosPar = posicaoPar * 3;
            //Soma das etapas 3 e 2
            somaEtapas = posicaoImpar + multPosPar;
        } else {
            //Multiplicação da etapa 1 por 3
            int multPosImpar = posicaoImpar * 3;
            //Soma das etapas 3 e 2
            somaEtapas = posicaoPar + multPosImpar;
        }

        String verificador = "";

        if ((somaEtapas + 0) % 10 == 0) {
            verificador = "0";
        }
        if ((somaEtapas + 1) % 10 == 0) {
            verificador = "1";
        }
        if ((somaEtapas + 2) % 10 == 0) {
            verificador = "2";
        }
        if ((somaEtapas + 3) % 10 == 0) {
            verificador = "3";
        }
        if ((somaEtapas + 4) % 10 == 0) {
            verificador = "4";
        }
        if ((somaEtapas + 5) % 10 == 0) {
            verificador = "5";
        }
        if ((somaEtapas + 6) % 10 == 0) {
            verificador = "6";
        }
        if ((somaEtapas + 7) % 10 == 0) {
            verificador = "7";
        }
        if ((somaEtapas + 8) % 10 == 0) {
            verificador = "8";
        }
        if ((somaEtapas + 9) % 10 == 0) {
            verificador = "9";
        }

        return verificador;
    }

    /**
     * Usado para validar número usado em balanças que emitem etiqueta.
     *
     * @param codigo
     * @param qtdCodigo
     * @return String[]
     * @throws NullPointerException
     */
    public static String[] validaCodePrix(String codigo, int qtdCodigo) 
            throws NullPointerException {
        String[] retorno = new String[3];

        qtdCodigo++;

        if (codigo.length() == 13) {
            retorno[0] = codigo.substring(0, 1);
            retorno[1] = codigo.substring(1, qtdCodigo);
            retorno[2] = codigo.substring(qtdCodigo, codigo.length());

            return retorno;
        } else {
            throw new NullPointerException("Não é um número de 13 caracteres ");
        }
    }

    /**
     * Retira o dígito verificador do código de barras.
     *
     * @param barCode
     * @param ean
     * @return String
     */
    private static String retiraDigitoVerificador(String barCode, Tipo ean) {
        //Retira qualquer caracter que não for numérico
        String barCodeSemVerificador = removeCaracteres(barCode);
        //Se tiver mais dígitos que o esperado
        if (barCodeSemVerificador.length() > ean.getQuantidade()) {
            throw new RuntimeException("A String do código de barras tem mais dígitos que o esperado.");
        }
        //Se tiver mais dígitos que o esperado
        barCodeSemVerificador = barCodeSemVerificador.substring(0, barCode.length() - 1);
        //Adiciona zeros à esquerda se tiver menos que o necessário
        barCodeSemVerificador = ManipuleString.lpad(barCodeSemVerificador, "0", ean.getQuantidade() - 1);

        return barCodeSemVerificador;
    }
    
    /**
     * Verifica se é uma chave de NF-e válida.
     * 
     * @param codUF
     * @param dtEmissao
     * @param cnpj
     * @param modelo
     * @param serie
     * @param numeroDocumentoFiscal
     * @param codNF
     * @param chaveNFe
     * @return boolean
     * @throws ChaveNFe2Exception
     */
    public static boolean isChaveNFe1(int codUF, Date dtEmissao, String cnpj, 
            int modelo, int serie, int numeroDocumentoFiscal, int codNF, 
            String chaveNFe) throws ChaveNFe2Exception {
        
        int formaEmissao = Integer.parseInt((codNF+"").substring(0,1));
        
        return isChaveNFe2(codUF, dtEmissao, cnpj, modelo, serie, numeroDocumentoFiscal, formaEmissao, codNF, chaveNFe);
   }
    
    /**
     * Verifica se é uma chave de NF-e válida.
     * 
     * @param codUF
     * @param dtEmissao
     * @param cnpj
     * @param modelo
     * @param serie
     * @param numeroDocumentoFiscal
     * @param formaEmissao
     * @param codNF
     * @param chaveNFe
     * @return boolean
     * @throws ChaveNFe2Exception 
     */
    public static boolean isChaveNFe2(int codUF, Date dtEmissao, String cnpj, 
            int modelo, int serie, int numeroDocumentoFiscal, int formaEmissao, 
            int codNF, String chaveNFe) throws ChaveNFe2Exception {
        //Retira qualquer caracter que não for numérico
        chaveNFe = removeCaracteres(chaveNFe);
        //Se tiver 44 dígitos numéricos
        if(chaveNFe.length() == 44) {
            int coluna = 1;

            while(coluna <= 9) {
                switch(coluna) {
                    case 1:
                        int result1 = Integer.parseInt(chaveNFe.substring(0, 2));

                        if(codUF != result1) {
                            throw new ChaveNFe2Exception("O Código da UF do emitente "
                                    + "do Documento Fiscal não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.CODIGOUF);
                        }
                        break;
                    case 2:
                        String data = DataHora.converterDateEmString(dtEmissao,
                                DataHora.Formato.PT_DATA_SEPARADOR_BARRA_ANO_2DIGITOS);
                        String[] dataQuebrada = data.split("/");
                        String dataAnoMes = dataQuebrada[2]+dataQuebrada[1];
                        String result2 = chaveNFe.substring(2, 6);

                        if(!dataAnoMes.equals(result2)) {
                            throw new ChaveNFe2Exception("O Ano e Mês de emissão da NF-e "
                                    + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.ANOMESEMISSAO);
                        }
                        break;
                    case 3:
                        cnpj = cnpj.replaceAll("[^0-9]+", "");

                        if(cnpj.length() == 14) {
                            String result3 = chaveNFe.substring(6, 20);

                            if(!cnpj.equals(result3)) {
                                throw new ChaveNFe2Exception("O CNPJ do emitente da NF-e "
                                        + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.CNPJEMITENTE);
                            }
                        } else {
                            throw new ChaveNFe2Exception("O CNPJ deve ter 14 dígitos numéricos.", 
                                    ChaveNFe2Exception.Coluna.CNPJEMITENTE);
                        }
                        break;
                    case 4:
                        int result4 = Integer.parseInt(chaveNFe.substring(20, 22));

                        if(modelo != result4) {
                            throw new ChaveNFe2Exception("O Modelo da NF-e "
                                    + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.MODELO);
                        }
                        break;
                    case 5:
                        int result5 = Integer.parseInt(chaveNFe.substring(22, 25));

                        if(serie != result5) {
                            throw new ChaveNFe2Exception("A Série da NF-e "
                                    + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.SERIE);
                        }
                        break;
                    case 6:
                        int result6 = Integer.parseInt(chaveNFe.substring(25, 34));

                        if(numeroDocumentoFiscal != result6) {
                            throw new ChaveNFe2Exception("O Número da NF-e "
                                    + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.NUMERONFE);
                        }
                        break;
                    case 7:
                        int result7 = Integer.parseInt(chaveNFe.substring(34, 35));

                        if(formaEmissao != result7) {
                            throw new ChaveNFe2Exception("A forma de emissão da NF-e "
                                    + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.FORMAEMISSAO);
                        }
                        break;
                    case 8:
                        int result8 = Integer.parseInt(chaveNFe.substring(35, 43));

                        if(codNF != result8) {
                            throw new ChaveNFe2Exception("O Código Numérico que compõe a Chave de Acesso "
                                    + "não condiz com o da chave enviada por parametro.", 
                                    ChaveNFe2Exception.Coluna.CODIGONUMERICO);
                        }
                        break;
                    case 9:
                        int result9 = Integer.parseInt(chaveNFe.substring(43, 44));
                        int dVGerado = geraDVChaveNFe(chaveNFe.substring(0, 43));

                        if(dVGerado != result9) {
                            throw new ChaveNFe2Exception("O Dígito Verificador da Chave de Acesso "
                                    + "está incorreto.", 
                                    ChaveNFe2Exception.Coluna.DV);
                        }
                        break;
                }
                coluna++;
            }
        } else {
            throw new NullPointerException("A chave da NF-e deve ter 44 dígitos numéricos.");
        }
        
        return true;
    }
    
    /**
     * Gera o dígito verificador da chave da NF-e.
     * 
     * @param chave43
     * @return int
     */
    public static int geraDVChaveNFe(String chave43) {
        //Retira qualquer caracter que não for numérico
        chave43 = removeCaracteres(chave43);
        //Se tiver 44 dígitos numéricos
        if(chave43.length() == 43) {
            int somaPonderacao = 0;
            int multiplicador = 2;
            
            for(int i=42; i>=0; i--) {
                int digito = Integer.parseInt(chave43.charAt(i)+"");
                somaPonderacao += digito * multiplicador;
                multiplicador++;
                multiplicador = (multiplicador > 9) ? 2 : multiplicador;
            }
            
            int resto = somaPonderacao % 11;
            int dVGerado = 11 - resto;

            if(resto == 0 || resto == 1) {
                dVGerado = 0;
            }
            
            return dVGerado;
        } else {
            throw new NullPointerException("Deve ser enviado apenas os 43 primeiros dígitos numéricos da chave da NF-e.");
        }
    }

    /**
     * Remove os caracteres não numéricos e faz verificações de consistência de
     * dados.
     *
     * @param valor
     * @return String
     */
    private static String removeCaracteres(String valor) {
        //Verifica se é nulo
        if (valor == null) {
            throw new NullPointerException("O código de barras deve ser diferente de nulo.");
        }
        //Retira qualquer caracter que não for numérico
        valor = valor.replaceAll("[^0-9]", "");
        //Verifica se é vazio
        if (ValidateString.isNullOrEmpty(valor)) {
            throw new RuntimeException("O código de barras não pode ser vazio.");
        }

        return valor;
    }
}
