package vn.rideshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.ResponseBody;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.ResponseCode;
import vn.rideshare.mapper.RideMapper;
import vn.rideshare.model.*;
import vn.rideshare.model.route.Leg;
import vn.rideshare.model.route.Route;
import vn.rideshare.repository.RideRepository;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.RideService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RideServiceImpl implements RideService {
    private static final long PREPARE_TIME_REQUIRE = 10l;
    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideMapper rideMapper;

    @Override
    public ResponseBody saveRide(SaveRideRequest request) {
        try {
            LocalDateTime startTime = request.getStartTime();
            LocalDateTime endTime = request.getEndTime();
            if (!LocalDateTime.now().plusMinutes(PREPARE_TIME_REQUIRE).isBefore(startTime)) {
                throw new CommonException(ResponseCode.START_TIME_MUST_BE_AFTER_10_MINUTES);
            } else if (!startTime.isBefore(endTime)) {
                throw new CommonException(ResponseCode.START_TIME_MUST_BE_AFTER_END_TIME);
            }
            Ride existRide = rideRepository.existOneActiveRideByUserId(request.getUserId());
            if (existRide != null && !Objects.equals(request.getId(), existRide.getId())) {
                throw new CommonException(ResponseCode.ONE_RIDE_ACTIVE);
            } else {
                Ride ride = rideMapper.toEntity(request);
                ride.setPath(getPathFromRoute(ride.getRoute()));
                ride = rideRepository.save(ride);
                return new ResponseBody(ResponseCode.SUCCESS,
                        rideMapper.toDto(ride));
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public ResponseBody updateRideStatus(UpdateStatusRequest request) {
        try {
            Ride ride = rideRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            if (request.getStatus().equals(EntityStatus.ACTIVE) || request.getStatus().equals(EntityStatus.PENDING)) {
                LocalDateTime startTime = ride.getStartTime();
                LocalDateTime endTime = ride.getEndTime();
                if (rideRepository.existOneActiveRideByUserId(ride.getUserId()) != null) {
                    throw new CommonException(ResponseCode.ONE_RIDE_ACTIVE);
                }
                if (!LocalDateTime.now().plusMinutes(PREPARE_TIME_REQUIRE).isBefore(startTime)) {
                    throw new CommonException(ResponseCode.START_TIME_MUST_BE_AFTER_10_MINUTES);
                }
                if (!startTime.isBefore(endTime)) {
                    throw new CommonException(ResponseCode.START_TIME_MUST_BE_AFTER_END_TIME);
                }
            }
            ride.setStatus(request.getStatus());
            rideRepository.save(ride);
            return new ResponseBody(ResponseCode.SUCCESS, true);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    @Override
    public RideDto findRideById(FindByIdRequest request) {
        try {
            return rideMapper.toDto(rideRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND)));
        } catch (CommonException e) {
            throw e;
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
            Ride ride = rideRepository.findById(request.getId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            User user = userRepository.findById(ride.getUserId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            return rideMapper.toFindRideDetailResponse(ride, user);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
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
                .forEach(step ->
                        step.getIntersections()
                                .stream()
                                .forEach(intersection ->
                                        coordinates.add(intersection.getLocation())
                                )
                );
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
}
