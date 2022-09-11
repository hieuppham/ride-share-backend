package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Maneuver{
    private String type;
    private String instruction;
    private int bearing_after;
    private int bearing_before;
    private List<Double> location;
    private int exit;
    private String modifier;
}
