package vn.rideshare.dto;

import lombok.*;
import vn.rideshare.model.Criteria;
import vn.rideshare.model.EntityStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RideRequestDto {
    private static List<Object> DEFAULT_COORDINATES;
    private List<Object> startCoordinates = DEFAULT_COORDINATES;
    private List<Object> endCoordinates = DEFAULT_COORDINATES;
    private double maxDistance = 1000;
    private LocalDateTime startTime = LocalDateTime.now();
    private String vehicleType = "both";
    private List<Criteria> criterions = new ArrayList<>();
    private EntityStatus status;

    static {
        DEFAULT_COORDINATES = new ArrayList<>();
        DEFAULT_COORDINATES.add(0, 105.82025213362483);
        DEFAULT_COORDINATES.add(1, 21.003226032483564);
    }
}


