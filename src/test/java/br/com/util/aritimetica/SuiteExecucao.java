package br.com.util.aritimetica;

import br.com.util.ansii.AnsiiTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Executando bateria de teste.
 */
//@RunWith(Suite.class)
//@SuiteClasses({
//        AlgebraFloat2Test.class,
//        AnsiiTest.class
//})
public class SuiteExecucao {

    @BeforeClass
    public static void ante(){
        System.out.println("Antes de executar");
    }

    @AfterClass
    public static void depois(){
        System.out.println(" depois de executar");
    }
}
