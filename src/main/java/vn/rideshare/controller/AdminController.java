package vn.rideshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.rideshare.client.dto.ride.FindRidesAdminResponse;
import vn.rideshare.client.dto.user.FindUsersAdminResponse;
import vn.rideshare.client.dto.user.UserDto;
import vn.rideshare.service.RideService;
import vn.rideshare.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RideService rideService;
    @Autowired
    private UserService userService;

    @PostMapping("/ride/find-all")
    public ResponseEntity<List<FindRidesAdminResponse>> findAllRides() {
        return ResponseEntity.ok()
                .body(rideService.findAllRidesAdmin());
    }

    @PostMapping("/user/find-all")
    public ResponseEntity<List<FindUsersAdminResponse>> findAllUser() {
        return ResponseEntity.ok()
                .body(userService.findUsersAdmin());
    }
}
