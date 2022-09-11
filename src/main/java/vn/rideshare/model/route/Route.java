package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Route {
    private boolean country_crossed;
    private String weight_name;
    private double weight;
    private double duration;
    private double distance;
    private List<Leg> legs;
    private String geometry;
}

