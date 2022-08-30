package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FindRidesByUserIdResponse {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<String> criterions = new ArrayList<>();
    private String note;
    private EntityStatus status = EntityStatus.UNKNOWN;
}
