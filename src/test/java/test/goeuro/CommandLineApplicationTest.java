package test.goeuro;

import org.junit.Test;
import test.goeuro.common.BaseTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * Created by uv on 25/11/2015 for GoEuroTest
 * An attempt to good test coverage for very simple example
 */
@SuppressWarnings("unused")
public class CommandLineApplicationTest extends BaseTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCheckArgsMissing() throws Exception {
        String[] args = {};
        CommandLineApplication.checkArgs(args);
        // assert (false); // should not get here
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckArgsTooMany() throws Exception {
        String[] args = {"Berlin", "Tokyo"};
        CommandLineApplication.checkArgs(args);
        //assert (false);
    }

    @Test
    public void testCheckArgsOK() throws Exception {
        String[] args = {"Berlin"};
        CommandLineApplication.checkArgs(args);
    }

    // util to show system locale
    @Test
    public void testLocale() {
        System.out.println("LOCALE: " + Locale.getDefault());
    }

    // util to show cwd
    //@Ignore //to disable
    @Test
    public void testCWD() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("CWD: " + s);
    }

}
