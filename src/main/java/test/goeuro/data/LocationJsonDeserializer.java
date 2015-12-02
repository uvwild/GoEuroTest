package test.goeuro.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by uv on 01/12/2015 for GoEuroTest
 */
public class LocationJsonDeserializer<T> extends JsonDeserializer {

    @Override
    public Location deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Root root = jp.readValueAs(Root.class);

        Location loc = new Location();
        loc.set_id(root._id);
        loc.setName(root.name);
        loc.setType(root.type);
        if (root.geoPosition != null) {     // serialization of variable length fixed point numbers is tricky
            loc.setLatitude(root.geoPosition.latitude);
            loc.setLongitude(root.geoPosition.longitude);
        }

        return loc;
    }

    /* SAMPLE {
        "_id":376217,
        "key":null,
        "name":"Berlin",
        "fullName":"Berlin, Germany",
         "iata_airport_code":null,
         "type":"location",
         "country":"Germany",
         "geo_position": {"latitude":52.52437,"longitude":13.41053 },
         "locationId":8384,
         "inEurope":true,
         "countryCode":"DE",
         "coreCountry":true,
         "distance":null
         }
     */

    @SuppressWarnings("unused")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Root {
        public Long _id;
        public String key;
        public String name;
        public String fullName;
        public String iata_airport_code;
        public String type;
        @JsonProperty("geo_position")
        public GeoPosition geoPosition;
        public Long locationId;
        public Boolean inEurope;
        public String countryCode;
        public Boolean coreCountry;
        public Long distance;
    }

    @SuppressWarnings("unused")
    private static class GeoPosition {
        public BigDecimal longitude;
        public BigDecimal latitude;
    }
}
