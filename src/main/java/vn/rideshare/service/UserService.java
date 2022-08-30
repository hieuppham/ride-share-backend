package vn.rideshare.service;

import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.user.*;

import java.util.List;

public interface UserService {
    UserDto saveUser(SaveUserRequest saveUserRequest);

    UserDto updateUser(UpdateUserRequest updateUserRequest);

    UserDto getUserById(FindUserByIdRequest findUserByIdRequest);

    boolean updateStatus(UpdateStatusRequest updateUserStatusRequest);

    UserDto getUserByUid(FindUserByUidRequest findUserByUidRequest);

    List<FindUserByTextResponse> findUsersByText(FindUserByTextRequest request);

    List<FindUsersAdminResponse> findUsersAdmin();
}
