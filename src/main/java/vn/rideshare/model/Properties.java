package vn.rideshare.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Properties {
    private String id;
    private String startPointTitle;
    private String endPointTitle;
    private double distance;
}
