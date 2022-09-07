package vn.rideshare.repository;

import vn.rideshare.client.dto.ride.*;

import java.util.List;

public interface RideCustomRepository {
     List<FindRidesResponse> findRidesByBound(FindRidesByBoundRequest request);

     List<FindRidesResponse> findAllRides();

     List<FindRideDetailResponse> findRidesByUserId(String id);

}
