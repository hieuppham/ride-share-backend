package vn.rideshare.model.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapboxStreetsV8{
    @JsonProperty("class")
    public String streetClass;
}
