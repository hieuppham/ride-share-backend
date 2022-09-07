package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByTextResponse {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String photoURL;
}
