/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.util.aritimetica;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import br.com.util.string.ValidateString;

/**
 * Auxilio na manipulação das Operações financeiras básicas e outras.
 *
 * @author Heverton Cruz
 * @author Herberts Cruz
 * @version 2.0
 */
public abstract class AlgebraFloat {

    /**
     * Soma dois numeros.
     *
     * @param valor
     * @param asoma
     * @return BigDecimal
     */
    public static BigDecimal soma(BigDecimal valor, BigDecimal asoma) {
        // Realiza a soma
        return valor.add(asoma);
    }

    /**
     * Subtrai dois numeros, sendo o primeiro maior que o segundo.
     *
     * @param maiorNumero
     * @param menorNumero
     * @return String resultado
     */
    public static BigDecimal subtracao(BigDecimal maiorNumero, BigDecimal menorNumero) {
        return maiorNumero.subtract(menorNumero, new MathContext(10, RoundingMode.HALF_DOWN));
    }

    /**
     * Multiplica dois numero.
     *
     * @param qtd
     * @param valor
     * @return BigDecimal
     */
    public static BigDecimal multiplicacao(BigDecimal qtd, BigDecimal valor) {
        return qtd.multiply(valor, new MathContext(10, RoundingMode.HALF_DOWN));
    }

    /**
     * Multiplica dois numero e permite truncar o valor com o número de casas
     * decimais que se deseja ter.
     *
     * @param  qtd
     * @param  valor
     * @param  casasDecimais
     * @return BigDecimal
     */
    public static BigDecimal multiplicacao(BigDecimal qtd, BigDecimal valor, int casasDecimais) {
        return mascaraDoubleModeloAmericano(multiplicacao(qtd, valor), casasDecimais);
    }

    /**
     * Divide o valor de um numero.
     *
     * @param dividendo
     * @param divisor
     * @return BigDecimal
     */
    public static BigDecimal divisao(BigDecimal dividendo, BigDecimal divisor) {
        if (AlgebraFloat.comparaBigDecimalIgual(divisor, BigDecimal.ZERO)) {
            divisor = new BigDecimal(1);
        }

        return dividendo.divide(divisor, 4, RoundingMode.HALF_EVEN);
    }
    
    /**
     * Define em qual posição deseja realizar o arredendar
     * 
     * @param dividendo
     * @param divisor
     * @param numArredondamento
     * @return 
     */
    public static BigDecimal divisaoCeill(BigDecimal dividendo, BigDecimal divisor, int numArredondamento) {
        if (AlgebraFloat.comparaBigDecimalIgual(divisor, BigDecimal.ZERO)) {
            divisor = new BigDecimal(1);
        }
        
        return dividendo.divide(divisor, numArredondamento, RoundingMode.HALF_EVEN);
    }

    /**
     * Divide o valor de um numero e permite truncar o valor com o número de
     * casas decimais que se deseja ter.
     *
     * @param  dividendo
     * @param  divisor
     * @param  casasDecimais
     * @return BigDecimal
     */
    public static BigDecimal divisao(BigDecimal dividendo, BigDecimal divisor, int casasDecimais) {
        return mascaraDoubleModeloAmericano(divisao(dividendo, divisor), casasDecimais);
    }

    /**
     * Faz o calculo da porcentagem de um produto.
     *
     * @param  valorItem
     * @param  qtdPorc
     * @return BigDecimal valor na proporção da porcentagem
     */
    public static BigDecimal calculoPorcetagem(BigDecimal valorItem, BigDecimal qtdPorc) {
        
        // Se o valor da porcentagem for zero retorna o valor zero
        if(comparaBigDecimalIgual(qtdPorc, new BigDecimal("0.0"))){
            return new BigDecimal("0.0");
        }
        
        // Valor em 100%
        BigDecimal res = divisao(valorItem, new BigDecimal("100.00"));
        // 100% dividido pelo valor total
        res = multiplicacao(res, qtdPorc);

        return res;
    }

    /**
     * Faz o calculo do reverso da porcentagem
     *
     * @param  valorResultantePorcentagem
     * @param  total
     * @return BigDecimal valor da porcentagem
     */
    public static BigDecimal calculoReversoPorcetagem(BigDecimal valorResultantePorcentagem, BigDecimal total) {
        BigDecimal res = multiplicacao(valorResultantePorcentagem, new BigDecimal("100.00"));
        // 100% dividido pelo valor total
        res = divisao(res, total);

        return res;
    }

    /**
     * Arredonda para cima n casas decimais
     *
     * @param  valor
     * @return BigDecimal
     */    
    public static BigDecimal ceillCima(BigDecimal valor, int casasDecimais){
        return ceillCimaAux(valor, casasDecimais);
    }
    
    /**
     * Arredonda para cima 2 casas decimasi
     *
     * @param valor valor
     * @return BigDecimal
     */
    public static BigDecimal ceillCima(BigDecimal valor){
        return ceillCimaAux(valor, 2);
    }
    
    private static BigDecimal ceillCimaAux(BigDecimal valor, int casasDecimais) {
        BigDecimal big = valor;
        big = big.setScale(casasDecimais, BigDecimal.ROUND_UP);
        return big;
    }

    /**
     * Arredonda para cima ou baixo a anteriores a teceira casa decimal. Usada
     * para calculos financeiro
     *
     * @param valor
     * @return BigDecimal valor_arredondado
     */    
    public static BigDecimal ceilCimaBaixo(BigDecimal valor) {
        return aceilCimaBaixoScale(valor, 2);
    }
    
    public static BigDecimal ceilCimaBaixo(BigDecimal valor, int casaDecimal) {
        return aceilCimaBaixoScale(valor, casaDecimal);
    }
    
    private static BigDecimal aceilCimaBaixoScale(BigDecimal valor, int casaDecimal) {
        // Arredonda para cima ou baixo a anteriores a teceira casa decimal
        return valor.setScale(casaDecimal, BigDecimal.ROUND_HALF_EVEN);
    }
    
    @Deprecated
    private static BigDecimal aceilCimaBaixo(BigDecimal valor, int casaDecimal) {
        // Arredonda para cima ou baixo a anteriores a teceira casa decimal
        valor = valor.setScale(casaDecimal, BigDecimal.ROUND_HALF_EVEN);

        String converte = String.valueOf(valor);
        // Pega o valor da 3º casa decimal.
        int w = converte.length();
        w = (w - 1);
        int it = Integer.parseInt(converte.charAt(w) + "");
        /* Caso a terceira casa seja menor que 5 corte a 3º casa
         * depois da virgula.
         * Se a 3º casa for maior que 5 joga para DecimalFormat que ele
         * arredonada automatico para cima.
         */
        if (it < 5) {
            StringBuilder frase = new StringBuilder(converte);
            // Elimina a 3º casa decimal
            converte = "" + frase.deleteCharAt(w);
        } else {
            String strdecimal = "0";
            for(int cont=1; cont < casaDecimal; cont++){
                strdecimal = strdecimal+"0";  
            }
            
            DecimalFormat decimal = new DecimalFormat("0."+strdecimal, DecimalFormatSymbols.getInstance(Locale.US));
            Double vl_conv = Double.parseDouble(converte);
            converte = (decimal.format(vl_conv));
        }
        
        return new BigDecimal(converte);
    }
    
    /**
     * Coloca uma máscara na string para no formato brasileiro
     *
     * @param  valor
     * @param  casasDecimais
     * @return String
     */
    public static String mascaraDouble(BigDecimal valor, int casasDecimais) {
        DecimalFormat myFormatter = new DecimalFormat("##,###.###", new DecimalFormatSymbols(new Locale("pt", "BR")));
        myFormatter.setMaximumFractionDigits(casasDecimais);
        myFormatter.setMinimumFractionDigits(casasDecimais);

        return myFormatter.format(valor);
    }

    /**
     * Retira a máscara na string para no formato brasileiro. Já retira os
     * pontos e vírgulas
     *
     * @param  valor
     * @param  casasDecimais
     * @return String
     */
    public static String mascaraDoubleSemMarcacao(BigDecimal valor, int casasDecimais) {
        DecimalFormat myFormatter = new DecimalFormat("##,###.###", new DecimalFormatSymbols(new Locale("pt", "BR")));
        myFormatter.setMaximumFractionDigits(casasDecimais);
        myFormatter.setMinimumFractionDigits(casasDecimais);
        String res = myFormatter.format(valor);

        return ((res.replace(".", "")).replace(",", ""));
    }

    /**
     * Coloca uma máscara na string no formato Americano sem vírgulas.
     *
     * @param  valor
     * @return String
     */
    public static BigDecimal mascaraDoubleModeloAmericano(BigDecimal valor, int casasDecimais) {
        DecimalFormat myFormatter = new DecimalFormat("##,###.###", new DecimalFormatSymbols(Locale.US));
        myFormatter.setMaximumFractionDigits(casasDecimais);
        myFormatter.setMinimumFractionDigits(casasDecimais);
        String res = myFormatter.format(valor.doubleValue()).replace(",", "");

        return new BigDecimal(res);
    }

    /**
     * Recebe uma String com valor sem casas decimais e retorna um BegDecimal
     * com duas casa decimais.
     *
     * Ex.: String 00000 retorna um BigDecimal 000.00
     *
     * @param valor
     * @param quantidadeCasas
     * @return BigDecimal
     */
    public static BigDecimal transformaIntemDoubleCasasDecimais(String valor, int quantidadeCasas) {
        try {
            // Se for zero não executar o método.
            if (AlgebraFloat.comparaBigDecimalIgual(new BigDecimal(valor), BigDecimal.ZERO)) {
                throw new ArrayIndexOutOfBoundsException("Inteiro");
            }

            // 
            if (ValidateString.isNumber(valor, false, true) == true) {
                throw new ArrayIndexOutOfBoundsException("Fracionado não permitido");
            }

            int tamanho = valor.length();
            char[] v = valor.toCharArray();
            String vlr = "";
            String centavo = "";

            quantidadeCasas++;

            for (int i = 1; i < quantidadeCasas; i++) {
                centavo = v[tamanho - i] + centavo;
                v[tamanho - i] = ' ';
            }

            for (int i = v.length - 1; i >= 0; i--) {
                vlr = v[i] + vlr;
            }

            vlr = (vlr + "." + centavo).replace(" ", "");

            return new BigDecimal(vlr);
        } catch (NullPointerException e) {
            return BigDecimal.ZERO;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return new BigDecimal(valor);
        } finally {
        }
    }

    /**
     * Formata Décimal
     *
     * @param  valor
     * @return BigDecimal
     */
    public static BigDecimal formataDecimal(String valor) {
        BigDecimal vlr = new BigDecimal(valor);
        BigDecimal decimal = vlr.setScale(2, BigDecimal.ROUND_UP);

        return decimal;
    }

    /**
     * Truncar o valor. Exemplo: 1.9485 para 1,94.
     *
     * @param  value
     * @return BigDecimal
     */
    public static BigDecimal truncate(BigDecimal value) {
        return truncateAux(value, 2);
    }
    
    /**
     * Truncar o valor. Exemplo: 1.9485 para 1,n casas decimais.
     *
     * @param  value
     * @return BigDecimal
     */
    public static BigDecimal truncate(BigDecimal value, int casasDecimais) {
        return truncateAux(value, casasDecimais);
    }
    
    private static BigDecimal truncateAux(BigDecimal value, int casasDecimais) {
        try {
            return new BigDecimal(value.toString().substring(0, value.toString().indexOf(".") + (casasDecimais+1)));
        } catch(StringIndexOutOfBoundsException e){
            return value;
        }
    }

    /**
     * Trasforma os números para positivo caso seja negativo. Usando Math.abs()
     *
     * @param valor
     * @return String
     */
    public static BigDecimal trasformaPositivo(BigDecimal valor) throws NumberFormatException {
        return new BigDecimal(Math.abs(valor.doubleValue()));
    }

    /**
     * Compara dois números floats e retorna se for menor. Verifica se o
     * valor$a$ser$compara é menor que valor
     *
     * @param  valor$a$ser$compara
     * @param  valor
     * @return boolean
     */
    public static boolean comparaBigDecimalMenor(BigDecimal valor$a$ser$compara, BigDecimal valor) {
        // Verifica se o valor$a$ser$compara é menor que valor
        // -- Quando o resultado de (compareTo) 
        //    -1 o numero é menor, 
        //     0 representa igualdade e
        //     1 representa que é maior. 
        if (valor$a$ser$compara.compareTo(valor) == -1) {
            // valor$a$ser$compara menor
            return true;
        } else {
            // valor$a$ser$compara maior
            return false;
        }
    }

    /**
     * Compara dois números floats e retorna se for menor ou igual. Verifica se
     * o valor$a$ser$compara é menor que valor ou igual valor
     *
     * @param  valor$a$ser$compara
     * @param  valor
     * @return boolean
     */
    public static boolean comparaBigDecimalMenorIgual(BigDecimal valor$a$ser$compara, BigDecimal valor) {
        // Verifica se o valor$a$ser$compara é menor que valor ou igual a valor
        // -- Quando o resultado de (compareTo) 
        //    -1 o numero é menor, 
        //     0 representa igualdade e
        //     1 representa que é maior. 
        if (valor$a$ser$compara.compareTo(valor) == -1 || valor$a$ser$compara.compareTo(valor) == 0) {
            // valor$a$ser$compara menor
            return true;
        } else {
            // valor$a$ser$compara maior
            return false;
        }
    }

    /**
     * Compara dois números floats e retorna se for maior. Verifica se o
     * valor$a$ser$compara é maior que valor
     *
     * @param  valor$a$ser$compara
     * @param  valor
     * @return boolean
     */
    public static boolean comparaBigDecimalMaior(BigDecimal valor$a$ser$compara, BigDecimal valor) {
        // Verifica se o valor$a$ser$compara é maior que valor
        // -- Quando o resultado de (compareTo) 
        //    -1 o numero é menor, 
        //     0 representa igualdade e
        //     1 representa que é maior. 
        if (valor$a$ser$compara.compareTo(valor) == 1) {
            // valor$a$ser$compara menor
            return true;
        } else {
            // valor$a$ser$compara maior
            return false;
        }
    }

    /**
     * Compara dois números floats e retorna se for maior ou igual. Verifica se
     * o valor$a$ser$compara é maior que valor ou igual valor
     *
     * @param  valor$a$ser$compara
     * @param  valor
     * @return boolean
     */
    public static boolean comparaBigDecimalMaiorIgual(BigDecimal valor$a$ser$compara, BigDecimal valor) {
        // Verifica se o valor$a$ser$compara é maior que valor ou igual a valor
        // -- Quando o resultado de (compareTo) 
        //    -1 o numero é menor, 
        //     0 representa igualdade e
        //     1 representa que é maior. 
        if (valor$a$ser$compara.compareTo(valor) == 1 || valor$a$ser$compara.compareTo(valor) == 0) {
            // valor$a$ser$compara menor
            return true;
        } else {
            // valor$a$ser$compara maior
            return false;
        }
    }

    /**
     * Compara dois números floats e retorna se for Igual. Verifica se o
     * valor$a$ser$compara é Igual a valor
     *
     * @param  valor$a$ser$compara
     * @param  valor
     * @return boolean
     */
    public static boolean comparaBigDecimalIgual(BigDecimal valor$a$ser$compara, BigDecimal valor) {
        // Verifica se o valor$a$ser$compara é maior que valor
        // -- Quando o resultado de (compareTo) 
        //    -1 o numero é menor, 
        //     0 representa igualdade e
        //     1 representa que é maior. 
        if (valor$a$ser$compara.compareTo(valor) == 0) {
            // valor$a$ser$compara igual
            return true;
        } else {
            // valor$a$ser$compara diferente
            return false;
        }
    }
}
