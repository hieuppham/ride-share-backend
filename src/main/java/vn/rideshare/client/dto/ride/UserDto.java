package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String id;
    private String email;
    private String photoURL;
    private String fullName;
    private String phone;
    private String gender;
}
