package test.goeuro.factories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by uv on 01/12/2015 for GoEuroTest
 * moved the configuration of the object mapper into its own factory class to be cleaner....
 */
@Configuration
public class ObjectMapperFactory {

    private static ObjectMapper objectMapper;

    /**
     * here we configure the big decimal for the correct conversion of the fixed point numbers
     * @return the objectmapper for json conversion
     */
    @Bean(name = "objectMapper")
    public ObjectMapper createObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,true);
        }
        return objectMapper;
    }

}
