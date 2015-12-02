package test.goeuro.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import test.goeuro.common.BaseTest;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by uv on 01/12/2015 for GoEuroTest
 * helpful tests to get uneven json mapping working
 */
@SuppressWarnings("unused")
public class LocationJsonDeserializerTest extends BaseTest {

    private JSONObject jsonObject;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        jsonObject = (JSONObject) jsonResponse.get(0);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testDeserializeLocation() throws Exception {
        Location loc = objectMapper.readValue(jsonObject.toString(), Location.class);
        assertEquals(testArg, loc.getName());
        assertEquals(52.52437d, loc.getLatitude().doubleValue(), 0.00001);  // use 10E-05delta for float comparison
        assertEquals(13.41053d, loc.getLongitude().doubleValue(), 0.00001);
    }

    @Test
    public void testDeserializeMap() throws Exception {
        @SuppressWarnings("unchecked") Map<String,Object> locationData = objectMapper.readValue(jsonObject.toString(), Map.class);
        for (Field field: Location.class.getFields()) {
            String fieldName = field.getName();
            if (locationData.containsKey(fieldName)) {
                assertNotNull(locationData.get(fieldName));
            } else {
                assertFalse("could not find key " + fieldName, true);
            }
        }
    }

}
