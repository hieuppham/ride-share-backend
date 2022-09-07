package vn.rideshare.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Feature {
    private String type = "Feature";
    private Geometry geometry;
    private Properties properties;

    public Feature(Geometry geometry){
        this.geometry = geometry;
    }
}
