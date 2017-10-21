package br.com.util.ansii;

import org.junit.Assert;
import org.junit.Test;

public class AnsiiTest {

    @Test
    public void verificarAnsiiCondicao(){
        Ansii ansii = new Ansii();
        // Teste de aceitação de condição STRING
        Assert.assertEquals("Linha: Foi apertado a tecla", 65, ansii.getArrayAnsii("A"));
    }

    @Test
    public void verificarAnsiiCondicaoString(){
        Ansii ansii = new Ansii();
        // Teste de aceitação de condição INT
        Assert.assertEquals("Linha: Foi apertado o código", "112", ansii.getArrayAnsiiStr(112));
    }
}
