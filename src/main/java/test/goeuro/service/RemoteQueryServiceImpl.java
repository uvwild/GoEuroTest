package test.goeuro.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import test.goeuro.MainProperties;
import test.goeuro.data.Location;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 */
@Component
public class RemoteQueryServiceImpl implements RemoteQueryService {

    @Autowired
    RestOperations restTemplate;

    /**
     * @param arg the location name
     * @return a JSONArray of the full locationrecords converted from the restTemplate String response
     */
    @Override
    public JSONArray queryRemoteApiAsJson(String arg) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(MainProperties.getMainProperty(MainProperties.API_BASE_URL) + arg, String.class);
        String jsonBody = responseEntity.getBody();
        return new JSONArray(jsonBody);
    }


    /**
     * @param arg the location name
     * @return the listof Location Objects converted from the restResonse using HttpMessageConverter
     */
    @Override
    public Location[] queryRemoteApi(String arg) {
        ResponseEntity<Location[]> responseEntity = restTemplate.getForEntity(MainProperties.getMainProperty(MainProperties.API_BASE_URL) + arg, Location[].class);
        return responseEntity.getBody();
    }

}
