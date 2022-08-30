package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindRidesByBoundRequest {
    private List<Double> bottomLeft;
    private List<Double> upperRight;
}
