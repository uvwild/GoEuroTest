package test.goeuro.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import test.goeuro.data.Location;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 */
@Service
interface RemoteQueryService {

    JSONArray queryRemoteApiAsJson(String arg);

    Location[] queryRemoteApi(String arg);
}
