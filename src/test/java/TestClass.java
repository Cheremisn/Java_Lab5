import org.example.InjectorTest.Injector;
import org.example.SomeBean;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestClass {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void test() throws Exception {

        SomeBean someBean = (new Injector()).inject(new SomeBean());
        someBean.foo();
        Assert.assertEquals("AC", output.toString());

    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}
