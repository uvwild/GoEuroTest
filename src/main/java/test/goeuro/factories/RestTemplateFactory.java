package test.goeuro.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uv on 01/12/2015 for GoEuroTest
 */
@Configuration
public class RestTemplateFactory {

    // we moved the configuration of the object mapper into its own factory class and autowired the bean to be clean....
    @Autowired
    private ObjectMapper objectMapper;

    private static RestTemplate restTemplate;

    /**
     * additional signature for test code
     * @param objectMapper for testing
     * @return the restTemplate to query the external rest service
     */
    public RestTemplate createRestTemplate(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        return createRestTemplate();
    }

    @Bean(name = "restTemplate")
    public RestTemplate createRestTemplate() {
        if (restTemplate==null) {
            restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
            jsonMessageConverter.setObjectMapper(objectMapper);
            messageConverters.add(jsonMessageConverter);
            restTemplate.setMessageConverters(messageConverters);
        }
        return restTemplate;
    }
}
