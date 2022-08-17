package vn.rideshare.dto;

import lombok.*;
import vn.rideshare.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideResponeDto {
    private String _id;
    private List<String> criterions;
    private double distance;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private Vehicle vehicle;
    private String note;
    private EntityStatus status;
    private User user;
    private Feature startPoint;
    private Feature endPoint;
    private Feature path;
}
