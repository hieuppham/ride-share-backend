package vn.rideshare.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.rideshare.client.dto.user.*;
import vn.rideshare.mapper.UserMapper;
import vn.rideshare.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        return null;
    }

    @Override
    public List<UserDto> toDto(List<User> entityList) {
        return Arrays.stream(modelMapper.map(entityList, UserDto[].class)).collect(Collectors.toList());
    }

    @Override
    public User toEntity(SaveUserRequest saveUserRequest) {
        if (saveUserRequest == null) {
            return null;
        }
        return modelMapper.map(saveUserRequest, User.class);
    }

    @Override
    public User toEntity(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest == null) {
            return null;
        }
        return modelMapper.map(updateUserRequest, User.class);
    }

    @Override
    public List<FindUserByTextResponse> toFindByTextResponse(List<User> users) {
       return users.stream()
               .map(userEntity -> {
                   return modelMapper.map(userEntity, FindUserByTextResponse.class);
               }).collect(Collectors.toList());
    }

    @Override
    public List<FindUsersAdminResponse> toFindUsersAdminResponse(List<User> users) {
        return users.stream().map(userEntity -> {
            return modelMapper.map(userEntity, FindUsersAdminResponse.class);
        }).collect(Collectors.toList());
    }
}
