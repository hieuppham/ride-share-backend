package vn.rideshare.service;

import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.ResponseBody;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.ride.*;

import java.util.List;

public interface RideService {
    ResponseBody saveRide(SaveRideRequest request);

    ResponseBody updateRideStatus(UpdateStatusRequest request);

    RideDto findRideById(FindByIdRequest request);

    List<FindRidesResponse> findRidesByBound(FindRidesByBoundRequest request);

    List<FindRidesResponse> findAllRides();

    List<FindRideDetailResponse> findRidesByUserId(FindByIdRequest request);

    List<FindRidesAdminResponse> findAllRidesAdmin();

    FindRideDetailResponse findDetailById(FindByIdRequest request);

    FindRidesResponse findSingleRideById(FindByIdRequest request);
}
