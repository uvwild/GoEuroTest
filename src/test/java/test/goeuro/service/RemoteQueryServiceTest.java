package test.goeuro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import test.goeuro.MainProperties;
import test.goeuro.common.BaseTest;
import test.goeuro.common.MockingHelper;
import test.goeuro.data.Location;
import test.goeuro.factories.ObjectMapperFactory;
import test.goeuro.factories.RestTemplateFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 * This test does not need the spring context unless we autowire the bean instead of mocking!
 */
@SuppressWarnings("unused")
public class RemoteQueryServiceTest extends BaseTest {

    private final String restTemplateMemberName = "restTemplate";

    //@Autowired
    private RemoteQueryService remoteQueryService;

    //@Autowired
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        // instantiate the bean directly - make test fast by skipping spring context loading
        remoteQueryService = new RemoteQueryServiceImpl();
    }


    @Test
    public void testQueryRemoteApiAsJson() throws Exception {
        restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(MainProperties.getMainProperty(MainProperties.API_BASE_URL) + testArg, String.class))
                .thenReturn(new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK));
        MockingHelper.setPrivateField(remoteQueryService, restTemplateMemberName, restTemplate);
        JSONArray result = remoteQueryService.queryRemoteApiAsJson(testArg);
        JSONAssert.assertEquals(jsonResponse, result, JSONCompareMode.STRICT);
    }

    @Test
    public void testqueryRemoteApi() throws Exception {
        // to avoid spring context loading we need to create the objectMapper bean
        ObjectMapper objectMapper = new ObjectMapperFactory().createObjectMapper();
        // and call the RestTemplateFactory manually
        restTemplate = new RestTemplateFactory().createRestTemplate(objectMapper);
        MockingHelper.setPrivateField(remoteQueryService, restTemplateMemberName, restTemplate);

        HttpMessageConverter messageConverter = restTemplate.getMessageConverters().get(0);
        HttpInputMessage httpInputMessage = new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                // in windows without specifying CharSet the maven build fails with
                // com.fasterxml.jackson.databind.JsonMappingException: Invalid UTF-8 middle byte 0x6e
                return new ByteArrayInputStream(jsonResponse.toString().getBytes(Charset.forName("UTF-8")));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
        @SuppressWarnings("unchecked") Object o = messageConverter.read(Location[].class, httpInputMessage);
        assertTrue(o instanceof Location[]);
        Location[] locations = remoteQueryService.queryRemoteApi(testArg);
        assertArrayEquals(locations, (Location[]) o);
    }

}
