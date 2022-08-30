package vn.rideshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.service.RideService;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping("/save")
    public ResponseEntity<RideDto> saveRide(@RequestBody SaveRideRequest request) {
        return ResponseEntity.ok().body(rideService.saveRide(request));
    }

    @PostMapping("/update-status")
    public ResponseEntity<Boolean> updateStatus(@RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok()
                .body(rideService.updateRideStatus(request));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<RideDto> findById(@RequestBody FindByIdRequest request){
        return ResponseEntity.ok()
                .body(rideService.findRideById(request));
    }

    @PostMapping("/find-detail-by-id")
    public ResponseEntity<FindRideDetailResponse> findDetailById(@RequestBody FindByIdRequest request){
        return ResponseEntity.ok()
                .body(rideService.findDetailById(request));
    }

    @PostMapping("/find-by-bound")
    public ResponseEntity<List<FindRidesResponse>> findRidesByBound(@RequestBody FindRidesByBoundRequest request){
        return ResponseEntity.ok()
                .body(rideService.findRidesByBound(request));
    }

    @PostMapping("/find-by-user-id")
    public ResponseEntity<List<FindRidesResponse>> findRidesByUserId(@RequestBody FindByIdRequest request) {
        return ResponseEntity.ok()
                .body(rideService.findRidesByUserId(request));
    }

    @PostMapping("/find-all")
    public ResponseEntity<List<FindRidesResponse>> findAllRides() {
        return ResponseEntity.ok()
                .body(rideService.findAllRides());
    }
}
