package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.route.Route;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SaveRideRequest {
    private String id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String note;
    private List<String> criterions;
    private int vehicleId;
    private Route route;
}
