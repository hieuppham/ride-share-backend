package vn.rideshare.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.rideshare.model.route.Route;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("ride")
public class Ride implements Serializable {
    @Transient
    public EntityStatus savedState;

    @Id
    private String id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int vehicleId;
    private Route route;
    private List<String> criterions = new ArrayList<>();
    private String note;
    private EntityStatus status = EntityStatus.PENDING;
    private Feature path;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
