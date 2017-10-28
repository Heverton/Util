package br.com.util.ansii;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import java.lang.NullPointerException;

/**
 * Created by heverton on 28/10/17.
 */
public class UsandoRoleTest {
    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = NullPointerException.class)
    public void teste2() throws Exception {
        Ansii ansii = new Ansii();
        throw new NullPointerException("OK");
    }

    @Test
    public void teste3() throws Exception {
        Ansii ansii = new Ansii();
        //Outra forma de fazer
        error.checkThat(65, CoreMatchers.is(ansii.getArrayAnsii("A")));
    }
}
