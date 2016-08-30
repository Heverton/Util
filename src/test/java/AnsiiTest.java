import com.hsc.ansii.Ansii;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Heverton Cruz on 30/08/2016.
 */
public class AnsiiTest {

    @Test
    public void getArrayAnsii(){
        Ansii ansii = new Ansii();
        // Teste de aceitação de condição STRING
        TestCase.assertEquals("Linha: Foi apertado a tecla", 65, ansii.getArrayAnsii("A"));
        // Teste de aceitação de condição INT
        TestCase.assertEquals("Linha: Foi apertado o código", 112, ansii.getArrayAnsii(112));

    }
}
