package vn.rideshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.rideshare.dto.BoundRequestDto;
import vn.rideshare.dto.RequestUpdateStatusDto;
import vn.rideshare.dto.RideResponeDto;
import vn.rideshare.model.Ride;
import vn.rideshare.service.RideService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping("/create")
    public ResponseEntity<Ride> create(@RequestBody Ride ride) {
        return this.rideService.createRide(ride);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<List<Ride>> getRidesByUid(@PathVariable("uid") String uid) {return this.rideService.getRidesByUid(uid);}

    @GetMapping("/find")
    public ResponseEntity<List<RideResponeDto>> getAllActiveRides() {
        return this.rideService.getAllRides(true);
    }

    @PostMapping("/find-by-bound")
    public ResponseEntity<List<RideResponeDto>> getRidesByBound(@RequestBody BoundRequestDto boundRequestDto) {
        return this.rideService.getByBound(boundRequestDto);
    }

    @PutMapping("/status")
    public ResponseEntity<Boolean> updateStatus(@RequestBody RequestUpdateStatusDto request){
        return this.rideService.updateStatus(request);
    }

}
