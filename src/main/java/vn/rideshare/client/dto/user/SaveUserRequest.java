package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserRequest {
    private String uid;
    private String email;
    private String photoURL;
}
