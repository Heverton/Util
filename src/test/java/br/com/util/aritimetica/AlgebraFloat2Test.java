package br.com.util.aritimetica;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class AlgebraFloat2Test {

    @Test
    public void somarDoisValores() {
        BigDecimal result = AlgebraFloat.soma(new BigDecimal("75"), new BigDecimal("75"));
        Assert.assertEquals("Resultado: ", new BigDecimal("150"), result);
    }

    @Test
    public void subtracao() {
        BigDecimal result = AlgebraFloat.subtracao(new BigDecimal("75"), new BigDecimal("75"));
        Assert.assertEquals("Resultado: ", new BigDecimal("0"), result);
    }

    @Test
    public void multiplicacao() {
        BigDecimal result = AlgebraFloat.multiplicacao(new BigDecimal("5"), new BigDecimal("100"));
        Assert.assertEquals("Resultado: ", new BigDecimal("500"), result);
    }

    @Test
    public void multiplicacaoCasasDecimais() {
        BigDecimal result = AlgebraFloat.multiplicacao(new BigDecimal("5"), new BigDecimal("100"), 2);
        Assert.assertEquals("Resultado: ", new BigDecimal("500.00"), result);
    }

    @Test
    public void divisao() {
        BigDecimal result = AlgebraFloat.divisao(new BigDecimal("100"), new BigDecimal("10"));
        Assert.assertEquals("Resultado: ", new BigDecimal("10.0000"), result);
    }

    @Test
    public void divisaoCeill() {
        BigDecimal result = AlgebraFloat.divisaoCeill(new BigDecimal("100"), new BigDecimal("9"), 1);
        Assert.assertEquals("Resultado: ", new BigDecimal("11.1"), result);
    }

    @Test
    public void divisaoComPrecisaoDeDuasCasas() {
        BigDecimal result = AlgebraFloat.divisao(new BigDecimal("100"), new BigDecimal("10"), 2);
        Assert.assertEquals("Resultado: ", new BigDecimal("10.00"), result);
    }

    @Test
    public void calculoPorcetagem() {
        BigDecimal result = AlgebraFloat.calculoPorcetagem(new BigDecimal("100"), new BigDecimal("50"));
        Assert.assertEquals("Resultado: ", new BigDecimal("50.0000"), result);
    }

    @Test
    public void calculoReversoPorcetagem() {
        BigDecimal result = AlgebraFloat.calculoReversoPorcetagem(new BigDecimal("50"), new BigDecimal("100"));
        Assert.assertEquals("Resultado: ", new BigDecimal("50.0000"), result);
    }

    @Test
    public void ceillCima(){
        BigDecimal result = AlgebraFloat.ceillCima(new BigDecimal("50"), 2);
        Assert.assertEquals("Resultado: ", new BigDecimal("50.00"), result);
    }

    @Test
    public void ceillCima2(){
        BigDecimal result = AlgebraFloat.ceillCima(new BigDecimal("50"));
        Assert.assertThat("Resultado: ",result, new UsandoTypeSafeMatcher(new BigDecimal("50.00")));
    }

//    public static BigDecimal ceilCimaBaixo(BigDecimal valor) {
//    }

//    public static BigDecimal ceilCimaBaixo(BigDecimal valor, int casaDecimal) {
//    }

//    public static String mascaraDouble(BigDecimal valor, int casasDecimais) {
//    }

//    public static String mascaraDoubleSemMarcacao(BigDecimal valor, int casasDecimais) {
//    }

//    public static BigDecimal mascaraDoubleModeloAmericano(BigDecimal valor, int casasDecimais) {
//    }

//    public static BigDecimal transformaIntemDoubleCasasDecimais(String valor, int quantidadeCasas) {
//    }
//
//    public static BigDecimal formataDecimal(String valor) {
//    }

//    public static BigDecimal truncate(BigDecimal value) {
//    }

//    public static BigDecimal truncate(BigDecimal value, int casasDecimais) {
//    }

//    public static BigDecimal trasformaPositivo(BigDecimal valor) throws NumberFormatException {
//    }

//    public static boolean comparaBigDecimalMenor(BigDecimal valor$a$ser$compara, BigDecimal valor) {
//    }

//    public static boolean comparaBigDecimalMenorIgual(BigDecimal valor$a$ser$compara, BigDecimal valor) {
//    }

//    public static boolean comparaBigDecimalMaior(BigDecimal valor$a$ser$compara, BigDecimal valor) {
//    }

//    public static boolean comparaBigDecimalMaiorIgual(BigDecimal valor$a$ser$compara, BigDecimal valor) {
//    }

//    public static boolean comparaBigDecimalIgual(BigDecimal valor$a$ser$compara, BigDecimal valor) {
//    }

}

