package test.goeuro.common;

import java.lang.reflect.Field;

/**
 * Helper class to collect independent methods to improve code readability
 */
public class MockingHelper {

    public static void setPrivateField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {

        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);      // access private field
        field.set(object, value);
    }
}
