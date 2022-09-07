package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Intersection{
    public List<Boolean> entry;
    public List<Integer> bearings;
    public double duration;
    public MapboxStreetsV8 mapbox_streets_v8;
    public boolean is_urban;
    public int admin_index;
    public int out;
    public double weight;
    public int geometry_index;
    public List<Double> location;
    public int in;
    public double turn_weight;
    public double turn_duration;
    public boolean traffic_signal;
    public List<Lane> lanes;
    public List<String> classes;
    public TollCollection toll_collection;
}
