package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Intersection{
    private List<Boolean> entry;
    private List<Integer> bearings;
    private double duration;
    private MapboxStreetsV8 mapbox_streets_v8;
    private boolean is_urban;
    private int admin_index;
    private int out;
    private double weight;
    private int geometry_index;
    private List<Double> location;
    private int in;
    private double turn_weight;
    private double turn_duration;
    private boolean traffic_signal;
    private List<Lane> lanes;
    private List<String> classes;
    private TollCollection toll_collection;
}
