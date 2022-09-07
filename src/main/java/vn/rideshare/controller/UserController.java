package vn.rideshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.user.*;
import vn.rideshare.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody SaveUserRequest request) {
        return ResponseEntity.ok().body(userService.saveUser(request));
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok().body(userService.updateUser(request));
    }

    @PostMapping("/find-short-info-by-id")
    public ResponseEntity<FindShortUserInfoResponse> findShortUserInfoById(@RequestBody FindByIdRequest request) {
        return ResponseEntity.ok()
                .body(userService.findShortUserInfoById(request));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<UserDto> findUserById(@RequestBody FindByIdRequest request) {
        return ResponseEntity.ok().body(userService.getUserById(request));
    }

    @PostMapping("/update-status")
    public ResponseEntity<Boolean> updateStatus(@RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok().body(userService.updateStatus(request));
    }

    @PostMapping("/find-by-uid")
    public ResponseEntity<UserDto> findUserByUid(@RequestBody FindUserByUidRequest request) {
        return ResponseEntity.ok().body(userService.getUserByUid(request));
    }

    @PostMapping("/search")
    public ResponseEntity<List<FindUserByTextResponse>> searchUser(@RequestBody FindUserByTextRequest request){
        return ResponseEntity.ok().body(userService.findUsersByText(request));
    }

}
