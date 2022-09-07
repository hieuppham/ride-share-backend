package vn.rideshare.mapper;

import vn.rideshare.client.dto.ride.*;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;

import java.util.List;

public interface RideMapper extends EntityMapper<RideDto, Ride>{
    List<FindRidesDetailResponse> toFindRidesByBoundResponse(List<Ride> entityList);
    Ride toEntity(SaveRideRequest request);

    List<FindRidesAdminResponse> toFindRidesAdminResponse(List<Ride> entityList);

    List<FindRidesByUserIdResponse> toFindRidesByUidResponse(List<Ride> entityList);

    FindRideDetailResponse toFindRideDetailResponse(Ride ride, User user);

}
