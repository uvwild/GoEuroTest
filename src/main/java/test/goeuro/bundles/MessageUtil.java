package test.goeuro.bundles;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 */
public class MessageUtil {
    private static final ResourceBundle myResources = ResourceBundle.getBundle("MessageResources");

    private static String getMessageString(String messageKey) {
        return myResources.getString(messageKey);
    }

    @SuppressWarnings("unused")
    public static String formatMessage(String messageKey) {
        MessageFormat mf = new MessageFormat(getMessageString(messageKey));
        return mf.format(new Object[0]);
    }

    public static String formatMessage(@SuppressWarnings("SameParameterValue") String messageKey, Object arg0) {
        MessageFormat mf = new MessageFormat(getMessageString(messageKey));
        Object[] args = new Object[1];
        args[0] = arg0;
        return mf.format(args);
    }

    // Add more implementations of formatMessage() for as many arguments as needed
}
