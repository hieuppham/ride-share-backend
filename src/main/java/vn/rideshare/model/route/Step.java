package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Step{
    private List<Intersection> intersections;
    private Maneuver maneuver;
    private String name;
    private double duration;
    private double distance;
    private String driving_side;
    private double weight;
    private String mode;
    private String geometry;
    private String destinations;
    private String ref;
}
