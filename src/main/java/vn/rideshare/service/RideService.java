package vn.rideshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.rideshare.dto.BoundRequestDto;
import vn.rideshare.dto.RequestUpdateStatusDto;
import vn.rideshare.dto.RideRequestDto;
import vn.rideshare.dto.RideResponeDto;
import vn.rideshare.model.Ride;
import vn.rideshare.repository.RideRepository;
import vn.rideshare.repository.impl.RideCustomRepository;

import java.util.List;

@Repository
public class RideService {
    @Autowired
    private RideRepository rideRepository;

    @Autowired
    RideCustomRepository rideCustomRepository;

    public ResponseEntity<Ride> createRide(Ride ride) {
        return ResponseEntity.ok()
                .body(this.rideRepository.save(ride));
    }

    public ResponseEntity<List<Ride>> getRidesByUid(String uid) {
        return ResponseEntity.ok()
                .body(this.rideRepository.getRidesByUid(uid));
    }

    public ResponseEntity<Boolean> updateStatus(RequestUpdateStatusDto request){
        this.rideRepository.updateStatus(request.getId(), request.getStatus());
        return ResponseEntity.ok()
                .body(true);
    }

    public ResponseEntity<List<RideResponeDto>> getAllRides(Boolean active){
        return ResponseEntity.ok()
                .body(this.rideCustomRepository.getAllRides(active));
    }

    public ResponseEntity<List<RideResponeDto>> getByBound(BoundRequestDto boundRequestDto){
        return ResponseEntity.ok()
                .body(this.rideCustomRepository.getByBound(boundRequestDto));
    }
}
