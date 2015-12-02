package test.goeuro;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.goeuro.common.BaseTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import static junit.framework.TestCase.assertTrue;

/**
 * Instead of putting the caller script in the maven lifecycle we use a simple java class and RunTime.
 * This way we can run this Integration Test easily with the surefire plugin.
 * Created by uv on 30/11/2015 for GoEuroTest
 */
@SuppressWarnings("unused")
public class CommandLineApplicationITest extends BaseTest {

    private static final Logger LOG = Logger.getLogger(CommandLineApplicationITest.class.getSimpleName());

    private final boolean enableDebugging = "true".equalsIgnoreCase(MainProperties.getMainProperty(MainProperties.ENABLE_APP_DEBUG));
    private static final Integer javaDebugPort = 9999;
    private static final String waitForDebugger = "y";
    private static final String javaDebug = String.format("-agentlib:jdwp=transport=dt_socket,address=localhost:%d,suspend=%s,server=y", javaDebugPort, waitForDebugger);
    private File resultFile;
    private String fileName;

    // remove testfile before and after
    @Before
    public void setUp() {
        fileName = MainProperties.getMainProperty(MainProperties.TESTRESULT_FILENAME);
        resultFile = new File(fileName);
        if (resultFile.exists()) //noinspection ResultOfMethodCallIgnored
            resultFile.delete();
    }

    @After
    public void tearDown() {
        if (resultFile.exists()) //noinspection ResultOfMethodCallIgnored
            resultFile.delete();
    }

    @Test
    public void testCmdLine() throws IOException {

        Runtime runtime = Runtime.getRuntime();
        String jarDirectory = System.getProperty("maven.project.target");
        String jarName = System.getProperty("maven.project.finalName");
        // always set test Flag so the application use the configured testfile name
        String cmd = String.format("java %s -Dtest=true -jar %s%s%s-jar-with-dependencies.jar %s", enableDebugging ? javaDebug : "", jarDirectory, File.separator, jarName,
                testArg);

        System.err.println("EXEC: " + cmd);
        Process process = runtime.exec(cmd);

        // collect output/error streams
        InputStreamReader processStdErr = new InputStreamReader(process.getErrorStream());
        BufferedReader br = new BufferedReader(processStdErr);
        String line;
        while ((line = br.readLine()) != null) {
            System.err.println(line);
//            LOG.severe(line);
        }

        InputStreamReader processStdOut = new InputStreamReader(process.getInputStream());
        br = new BufferedReader(processStdOut);
        while ((line = br.readLine()) != null) {
            System.out.println(line);
//                LOG.fine("STDOUT: " + line);
        }
        checkOutputFile();
    }

    private void checkOutputFile() throws IOException {
        assertTrue("Output File " + fileName + " is missing", resultFile.exists());
        @SuppressWarnings("unchecked") List<String> fileContent = FileUtils.readLines(resultFile);
        for (String line : fileContent) {
            LOG.fine("FILECONTENT: " + line);
        }
        assertTrue(fileContent.size() > 6); // require 2 lines
    }

}
