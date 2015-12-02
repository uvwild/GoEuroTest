package test.goeuro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.goeuro.MainProperties;
import test.goeuro.data.Location;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 */
@Component
public class ResultWriterCSVImpl implements ResultWriter {

    // make sure we get the right filename for integration tests (started with debug flag)
    private final String csvFileName = MainProperties.getMainProperty("true".equalsIgnoreCase(System.getProperty("test"))?MainProperties
            .TESTRESULT_FILENAME:MainProperties.RESULT_FILENAME);

    private static final Logger LOG = Logger.getLogger(ResultWriterCSVImpl.class.getSimpleName());

    @Autowired
    private CSVFormat csvFormat;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * default CTOR needed for Spring as we have an additional testingCTOR
     */
    private ResultWriterCSVImpl() {
    }

    /**
     * non-public CTOR for testing
     */
    ResultWriterCSVImpl(CSVFormat csvFormat, ObjectMapper objectMapper) {
        this.csvFormat = csvFormat;
        this.objectMapper = objectMapper;
    }

    /**
     * Historical method which takes a json array into the location array before calling the writer.
     * (before I managed to configure the HttpMessageConverter)
     *
     * @throws IOException
     */
    @Override
    public void writeResponseAsJson(JSONArray jsonArray) throws IOException {
        Location[] locations = new Location[jsonArray.length()];
        Iterator iterator = jsonArray.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof JSONObject) {
                JSONObject jo = (JSONObject) o;
                // explicit conversion to Location objects
                locations[i++] = objectMapper.readValue(jo.toString(), Location.class);
            }
        }
        writeResponse(locations);
    }

    @Override
    public void writeResponse(Location[] locations) throws IOException {
        FileWriter fileWriter = new FileWriter(csvFileName);

        if (locations.length == 0) {
            LOG.severe("Empty Result received ==> No Output File written");
        } else {
            LOG.fine(String.format("Got Response ==> trying to write %d records to %s", locations.length, csvFileName));
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
            for (Location location : locations) {
                @SuppressWarnings("unchecked") Map<String, Object> props = objectMapper.convertValue(location, Map.class);
                // converting the object into a map simplifies CSV record output
                csvPrinter.printRecord(props.values());
            }
            csvPrinter.close();
            System.out.println(String.format("wrote %d records into %s", locations.length, csvFileName));
        }
    }
}
