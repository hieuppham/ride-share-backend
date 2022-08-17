package vn.rideshare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("ride")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ride {
    @Id
    private String id;
    private String uid;
    private double distance;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Vehicle vehicle;
    private Feature path;
    private Feature startPoint;
    private Feature endPoint;
    private List<String> criterions = new ArrayList<>();
    private String note;
    private EntityStatus status = EntityStatus.UNKNOWN;
}
