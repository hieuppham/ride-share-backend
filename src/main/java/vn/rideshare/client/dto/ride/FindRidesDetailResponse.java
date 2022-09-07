package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.Feature;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class FindRidesDetailResponse {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startPointTitle;
    private String endPointTitle;
    private String note;
    private List<String> criterions;
    private VehicleDto vehicle;
    private Double distance;
    private UserDto user;
    private Feature path;
}
