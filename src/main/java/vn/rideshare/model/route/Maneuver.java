package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Maneuver{
    public String type;
    public String instruction;
    public int bearing_after;
    public int bearing_before;
    public List<Double> location;
    public int exit;
    public String modifier;
}
