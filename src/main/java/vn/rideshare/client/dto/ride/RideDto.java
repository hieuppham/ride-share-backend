package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.route.Route;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RideDto {
    private String id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private VehicleDto vehicle;
    private Route route;
    private List<String> criterions = new ArrayList<>();
    private String note;
    private EntityStatus status = EntityStatus.UNKNOWN;
}
