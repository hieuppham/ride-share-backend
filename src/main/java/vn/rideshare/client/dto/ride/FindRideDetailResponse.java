package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FindRideDetailResponse {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startPointTitle;
    private String endPointTitle;
    private double distance;
    private String note;
    private List<String> criterions;
    private VehicleDto vehicle;
    private EntityStatus status;
    private UserDto user;
}
