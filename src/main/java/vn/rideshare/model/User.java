package vn.rideshare.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "user")
public class User {
    @Id
    private String id;

    private String uid;

    private String photoUrl;

    private String gender;

    private String fullName;

    private String email;

    private Date dob;

    private String phone;

    private List<Vehicle> vehicles = new ArrayList<>();

    private EntityStatus status = EntityStatus.UNKNOWN;
}
