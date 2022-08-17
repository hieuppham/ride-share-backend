package vn.rideshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.rideshare.dto.RequestUpdateStatusDto;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> upsertUser(@RequestBody User user) {
        return this.userService.upsertUser(user);
    }

    @PutMapping("/status")
    public ResponseEntity<Boolean> updateStatus(@RequestBody RequestUpdateStatusDto request) {
        return this.userService.updateStatus(request);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUser() {
        return this.userService.getAllUser();
    }

    @GetMapping("/{uid}")
    public ResponseEntity<User> getUserByUID(@PathVariable(name = "uid") String uid) {
        return this.userService.getUserByUID(uid);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam("what") String what){
        return this.userService.searchUser(what);
    }
}
