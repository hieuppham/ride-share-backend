package vn.rideshare.mapper;

import vn.rideshare.client.dto.user.*;
import vn.rideshare.model.User;

import java.util.List;

public interface UserMapper extends EntityMapper<UserDto, User> {
   User toEntity(SaveUserRequest saveUserRequest);
   User toEntity(UpdateUserRequest updateUserRequest);
   List<FindUserByTextResponse> toFindByTextResponse(List<User> users);

   List<FindUsersAdminResponse> toFindUsersAdminResponse(List<User> users);
}
