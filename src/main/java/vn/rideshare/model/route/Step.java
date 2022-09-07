package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Step{
    public List<Intersection> intersections;
    public Maneuver maneuver;
    public String name;
    public double duration;
    public double distance;
    public String driving_side;
    public double weight;
    public String mode;
    public String geometry;
    public String destinations;
    public String ref;
}
