package vn.rideshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.user.*;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.ResponseCode;
import vn.rideshare.mapper.UserMapper;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDto saveUser(SaveUserRequest saveUserRequest) {
        try {
            User user;
            Optional<User> optional = userRepository.findUserByUid(saveUserRequest.getUid());
            if (optional.isPresent()) {
                user = optional.get();
                user.setPhotoURL(saveUserRequest.getPhotoURL());
            } else {
                user = userMapper.toEntity(saveUserRequest);
            }
            user = userRepository.save(user);
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public UserDto updateUser(UpdateUserRequest updateUserRequest) {
        try {
            User user = userRepository.findById(updateUserRequest.getId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            user = userMapper.toEntity(user, updateUserRequest);
            userRepository.save(user);
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public boolean updateStatus(UpdateStatusRequest request) {
        try {
            User user = userRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            user.setStatus(request.getStatus());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public UserDto getUserByUid(FindUserByUidRequest request) {
        try {
            User user = userRepository.findUserByUid(request.getUid()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            return userMapper.toDto(user);
        }catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public List<FindUserByTextResponse> findUsersByText(FindUserByTextRequest request) {
        try {
            List<User> users = userRepository.findUsersByText(request.getText());
            return userMapper.toFindByTextResponse(users);
        }catch (Exception e){
            throw new CommonException(e);
        }
    }

    @Override
    public List<FindUsersAdminResponse> findUsersAdmin() {
        return userMapper.toFindUsersAdminResponse(userRepository.findAll(Sort.by("status").descending()));
    }

    @Override
    public FindShortUserInfoResponse findShortUserInfoById(FindByIdRequest request) {
        return userMapper.toShortInfo(userRepository.findById(request.getId()).get());
    }

    @Override
    public UserDto getUserById(FindByIdRequest request) {
        try {
            User user = userRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            return userMapper.toDto(user);
        }catch (Exception e){
            throw new CommonException(e);
        }
    }
}
