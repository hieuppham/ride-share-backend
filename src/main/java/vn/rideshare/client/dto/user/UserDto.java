package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private String id;
    private String uid;
    private String email;
    private String photoURL;
    private String fullName;
    private String phone;
    private Date dob;
    private String userIdPhotoURL;
    private List<VehicleDto> vehicles;
    private EntityStatus status;
    private String gender;
}
