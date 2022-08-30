package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Feature;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FindRidesResponse {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String vehicleType;
    private double distance;
    private String note;
    private List<String> criterions;
    private Feature path;
    private EntityStatus status;
    private String fullName;
    private String photoURL;
}
