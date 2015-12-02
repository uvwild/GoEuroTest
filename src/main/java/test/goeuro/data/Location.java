package test.goeuro.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 * POJO for CVS line, helpful for custom and incomplete deserialization of the larger JSON Map
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = LocationJsonDeserializer.class)
public class Location {
    private Long _id;
    private String name;
    private String type;
    private BigDecimal latitude;    // Floats are not working due to different precision of the data
    private BigDecimal longitude;   // ditto

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Location) && EqualsBuilder.reflectionEquals(this, obj);
    }
}
