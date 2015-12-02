package test.goeuro.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import test.goeuro.data.Location;

import java.io.IOException;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 */
@Service
interface ResultWriter {
    void writeResponseAsJson(JSONArray response) throws IOException;
    void writeResponse(Location[] locations) throws IOException;
}
