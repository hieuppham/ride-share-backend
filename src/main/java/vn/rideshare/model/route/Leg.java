package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Leg{
    private List<Object> via_waypoints;
    private List<Admin> admins;
    private double weight;
    private double duration;
    private List<Step> steps;
    private double distance;
    private String summary;
}
