package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Route {
    public boolean country_crossed;
    public String weight_name;
    public double weight;
    public double duration;
    public double distance;
    public List<Leg> legs;
    public String geometry;
}

