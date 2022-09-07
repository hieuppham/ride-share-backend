package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;

@Getter
@Setter
public class FindUsersAdminResponse {
    private String id;
    private String photoURL;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private EntityStatus status;
}
