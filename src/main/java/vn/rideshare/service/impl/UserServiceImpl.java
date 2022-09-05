package vn.rideshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.user.*;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.ErrorCode;
import vn.rideshare.common.MailAction;
import vn.rideshare.mapper.UserMapper;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.MailService;
import vn.rideshare.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;

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
            throw new CommonException(ErrorCode.INTERNAL_SYSTEM_ERROR);
        }
    }

    @Override
    public UserDto updateUser(UpdateUserRequest updateUserRequest) {
        try {
            User user = userRepository.save(
                    userMapper.toEntity(updateUserRequest)
            );
            mailService.sendMail(user.getEmail(), MailAction.UPDATE_USER, user);
            return userMapper.toDto(user);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SYSTEM_ERROR);
        }
    }

    @Override
    public boolean updateStatus(UpdateStatusRequest request) {
        try {
            User user = userRepository.findById(request.getId()).get();
            user.setStatus(request.getStatus());
            user = userRepository.save(user);
            if (request.isSendEmail()) {
                MailAction action = request.getStatus() == EntityStatus.ACTIVE ? MailAction.UPDATE_USER_STATUS_ACTIVE : MailAction.UPDATE_USER_STATUS_INACTIVE;
                mailService.sendMail(user.getEmail(), action, user);
            }
            return true;
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SYSTEM_ERROR);
        }
    }

    @Override
    public UserDto getUserByUid(FindUserByUidRequest findUserByUidRequest) {
        User user = userRepository.findUserByUid(findUserByUidRequest.getUid()).orElse(null);
        return userMapper.toDto(user);
    }

    @Override
    public List<FindUserByTextResponse> findUsersByText(FindUserByTextRequest request) {
        List<User> users = userRepository.findUsersByText(request.getText());
        return userMapper.toFindByTextResponse(users);
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
        User user = userRepository.findById(request.getId()).orElse(null);
        return userMapper.toDto(user);
    }
}
