package test.goeuro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 * There are many ways to implement properties.
 * Using String constants as keys leverages good IDE support with less boiler plate code.
 */
public class MainProperties {
    private static final Logger LOG = Logger.getLogger(MainProperties.class.getSimpleName());

    private Properties properties;
    private static final String mainPropertiesPath = "main.properties";

    public enum PARMTYPE { JSON, @SuppressWarnings("unused")OBJECT }

    // final key names
    public static final String API_BASE_URL = "API_BASE_URL";
    public static final String ENABLE_APP_DEBUG = "ENABLE_APP_DEBUG";
    public static final String RESULT_FILENAME = "RESULT_FILENAME";
    public static final String TESTRESULT_FILENAME = "TESTRESULT_FILENAME";
    public static final String CSV_HEADERS = "CSV_HEADERS";
    public static final String PARM_TYPE = "PARM_TYPE";

    // same data could come from application.yml properties with a @Configuration annotation
//    public String ApiBaseUrl;
//    public boolean EnableAppDebug;
//    public String ResultFilename;
//    public String TestResultFilename;
//    public String CsvHeaders;
//    public String ParmType;

    private static MainProperties instance;

    private MainProperties() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = classLoader.getResourceAsStream(mainPropertiesPath);

        if (resourceStream == null) {
            throw new IOException("ABORT: Properties not found in classpath");
        }
        properties = new Properties();
        properties.load(resourceStream);
    }

    // use delegation instead of inheritance
    private String getProperty(String propName) {
        return properties.getProperty(propName);
    }
    /**
     * @return singleton MainProperties getter
     * @throws IOException
     */
    private static MainProperties getInstance() throws IOException {
        if (instance == null)
            instance = new MainProperties();
        return instance;
    }

    /**
     * static accessor wrapper
     * @param propName the String constants above should be used to access the properties
     * @return the prop value
     */
    public static String getMainProperty(String propName)  {
        try {
            return getInstance().getProperty(propName);
        } catch (IOException ioe) {
            String msg = ("cannot instantiate MainProperties" + ioe.getLocalizedMessage());
            LOG.severe(msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * Using an enum type for a special property so its type safe. This concept can be extended to a hierarchical configuration approach which has full IDE
     * support.
     * @return the enum for our configurable modeswitch
     */
    public static PARMTYPE getTypeProperty(@SuppressWarnings("SameParameterValue") String propName)  {
        try {
            return PARMTYPE.valueOf(getInstance().getProperty(propName));
        } catch (IOException ioe) {
            String msg = ("cannot instantiate MainProperties" + ioe.getLocalizedMessage());
            LOG.severe(msg);
            throw new RuntimeException(msg);
        }
    }

}
