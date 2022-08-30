package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindRidesAdminResponse {
    private String id;
    private double distance;
    private String startPointTitle;
    private String endPointTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EntityStatus status;
}
