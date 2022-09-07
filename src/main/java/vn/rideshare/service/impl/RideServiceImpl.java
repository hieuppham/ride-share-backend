package vn.rideshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.ErrorCode;
import vn.rideshare.mapper.RideMapper;
import vn.rideshare.model.*;
import vn.rideshare.model.route.Leg;
import vn.rideshare.model.route.Route;
import vn.rideshare.repository.RideRepository;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.RideService;
import vn.rideshare.util.socket.SocketEvent;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {
    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideMapper rideMapper;

    @Override
    public RideDto saveRide(SaveRideRequest request) {
        try {
            Ride ride = rideMapper.toEntity(request);
            ride.setId(null);
            ride.setPath(getPathFromRoute(ride.getRoute()));
            ride = rideRepository.save(ride);
            return rideMapper.toDto(ride);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public boolean updateRideStatus(UpdateStatusRequest request) {
        try {
            Ride ride = rideRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
            ride.setStatus(request.getStatus());
            rideRepository.save(ride);
            return true;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public RideDto findRideById(FindByIdRequest request) {
        try {
            return rideMapper.toDto(rideRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND)));
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public List<FindRidesResponse> findRidesByBound(FindRidesByBoundRequest request) {
        return rideRepository.findRidesByBound(request);
    }

    @Override
    public List<FindRidesResponse> findAllRides() {
        return rideRepository.findAllRides();
    }

    @Override
    public List<FindRideDetailResponse> findRidesByUserId(FindByIdRequest request) {
        return rideRepository.findRidesByUserId(request.getId());
    }

    @Override
    public List<FindRidesAdminResponse> findAllRidesAdmin() {
        return rideMapper.toFindRidesAdminResponse(rideRepository.findAll(Sort.by("status").descending()));
    }

    @Override
    public FindRideDetailResponse findDetailById(FindByIdRequest request) {
        try {
            Ride ride = rideRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
            User user = userRepository.findById(ride.getUserId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
            return rideMapper.toFindRideDetailResponse(ride, user);
        }catch (Exception e){
            throw new CommonException(e);
        }
    }

    @Override
    public FindRidesResponse findSingleRideById(FindByIdRequest request) {
        return rideRepository.findSingleRideById(request);
    }

    private Feature getPathFromRoute(Route route) {
        Feature path = new Feature();
        Geometry geometry = new Geometry();
        Properties properties = new Properties();
        List<List<Double>> coordinates = new ArrayList<>();
        Leg leg = route.getLegs().get(0);
        leg.getSteps()
                .stream()
                .forEach(step -> {
                    step.getIntersections()
                            .stream()
                            .forEach(intersection -> {
                                coordinates.add(intersection.getLocation());
                            });
                });
        geometry.setCoordinates(coordinates);
        geometry.setType("LineString");
        path.setGeometry(geometry);

        String[] summary = leg.getSummary().split(",");
        properties.setStartPointTitle(summary[0]);
        properties.setEndPointTitle(summary[1]);
        properties.setDistance(leg.getDistance());
        path.setProperties(properties);
        return path;
    }

    private String getEvent(EntityStatus oldStatus, EntityStatus newStatus) {
        String event = SocketEvent.NOTHING;
        if ((oldStatus == EntityStatus.INACTIVE || oldStatus == EntityStatus.PENDING)
                && newStatus == EntityStatus.ACTIVE) {
            event = SocketEvent.RIDE_ADDED;
        } else if (oldStatus == EntityStatus.ACTIVE & (newStatus == EntityStatus.INACTIVE || newStatus == EntityStatus.PENDING)) {
            event = SocketEvent.RIDE_REMOVED;
        }
        return event;
    }
}
