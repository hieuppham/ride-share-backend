package vn.rideshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.rideshare.dto.RequestUpdateStatusDto;
import vn.rideshare.dto.RideResponeDto;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.service.RideService;
import vn.rideshare.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RideService rideService;

    @GetMapping("/user/get-all")
    public ResponseEntity<List<User>> getAllUsers(){
        return this.userService.getAllUser();
    }

    @GetMapping("/ride/get-all")
    public ResponseEntity<List<RideResponeDto>> getAllRides() {
        return this.rideService.getAllRides(null);
    }

    @PutMapping("/ride/update-status")
    public ResponseEntity<Boolean> updateRideStatus(@RequestBody RequestUpdateStatusDto request){
        return this.rideService.updateStatus(request);
    }

    @PutMapping("/user/update-status")
    public ResponseEntity<Boolean> updateUserStatus(@RequestBody RequestUpdateStatusDto request){
        return this.userService.updateStatus(request);
    }


}
