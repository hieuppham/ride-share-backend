package vn.rideshare.repository;

import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.model.Ride;

import java.util.List;

public interface RideCustomRepository {
     List<FindRidesResponse> findRidesByBound(FindRidesByBoundRequest request);

     List<FindRidesResponse> findAllRides();

     List<FindRideDetailResponse> findRidesByUserId(String id);

     FindRidesResponse findSingleRideById(FindByIdRequest request);

     Ride existOneActiveRideByUserId(String id);

     void findAndActivate();

     void findAndInactivate();
}
