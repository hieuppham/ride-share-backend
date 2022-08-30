package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserRequest {
    private String id;
    private String fullName;
    private String phone;
    private String userIdPhotoURL;
    private List<VehicleDto> vehicles;
}
