package vn.rideshare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.rideshare.model.route.Route;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("ride")
public class Ride {
    @Id
    private String id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int vehicleId;
    private Route route;
    private List<String> criterions = new ArrayList<>();
    private String note;
    private EntityStatus status = EntityStatus.UNKNOWN;
    private Feature path;
}
