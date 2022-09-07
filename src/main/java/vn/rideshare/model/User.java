package vn.rideshare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String photoURL;
    private String gender;
    private String fullName;
    private String email;
    private Date dob;
    private String phone;
    private List<Vehicle> vehicles = new ArrayList<>();
    private EntityStatus status = EntityStatus.UNKNOWN;
    private String userIdPhotoURL;
}
