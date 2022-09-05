package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateUserRequest {
    private String id;
    private Date dob;
    private String fullName;
    private String phone;
    private EntityStatus status = EntityStatus.PENDING;
    private String gender;
    private String userIdPhotoURL;
    private List<VehicleDto> vehicles;
}
