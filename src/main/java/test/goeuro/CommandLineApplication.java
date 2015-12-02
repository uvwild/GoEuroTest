package test.goeuro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import test.goeuro.bundles.MessageResourceKeys;
import test.goeuro.bundles.MessageUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by uv on 25/11/2015 for GoEuroTest
 * Check args before starting the spring application
 * @author ${USER}
 */
@SpringBootApplication
class CommandLineApplication {
    private static final Logger LOG = Logger.getLogger(CommandLineApplication.class.getSimpleName());

    public static void main(String[] args) {
        try {
            checkArgs(args);
        } catch (IllegalArgumentException iae) {
            LOG.log(Level.SEVERE, iae.getLocalizedMessage());
        }
        LOG.log(Level.INFO, "Before SpringContext in main with " + args[0]);
        SpringApplication.run(CommandLineApplication.class, args);
    }

    /**
     * has increased visibility for TDD testability
     * @param args the commandline arguments
     * @throws IllegalArgumentException
     */
    static void checkArgs(String[] args) throws IllegalArgumentException {
        if (args.length != 1) {
            throw new IllegalArgumentException(MessageUtil.formatMessage(MessageResourceKeys.MSG_WRONG_NO_OF_ARGUMENTS, args.length));
        }

    }
}
