package vn.rideshare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.FindByIdRequest;
import vn.rideshare.client.dto.UpdateStatusRequest;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.ErrorCode;
import vn.rideshare.common.MailAction;
import vn.rideshare.mapper.RideMapper;
import vn.rideshare.model.*;
import vn.rideshare.model.route.Leg;
import vn.rideshare.model.route.Route;
import vn.rideshare.repository.RideRepository;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.MailService;
import vn.rideshare.service.RideService;

import javax.mail.MessagingException;
import java.io.IOException;
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

    @Autowired
    private MailService mailService;

    @Override
    public RideDto saveRide(SaveRideRequest request) {
        try {
            Ride ride = rideMapper.toEntity(request);
            User user = userRepository.findById(request.getUserId()).get();
            ride.setId(null);
            ride.setPath(getPathFromRoute(ride.getRoute()));
            ride = rideRepository.save(ride);
            mailService.sendMail(user.getEmail(), MailAction.CREATE_RIDE, rideMapper.toFindRideDetailResponse(ride, user));
            return rideMapper.toDto(ride);
        } catch (MessagingException | MailException | IOException e) {
            throw new CommonException(e);
        } catch (CommonException e) {
            throw e;
        }
    }

    @Override
    public boolean updateRideStatus(UpdateStatusRequest request) {
        try {
            Ride ride = rideRepository.findById(request.getId()).get();
            User user = userRepository.findById(ride.getUserId()).get();
            ride.setStatus(request.getStatus());
            ride = rideRepository.save(ride);
            if (request.isSendEmail()) {
                MailAction action = request.getStatus() == EntityStatus.ACTIVE ? MailAction.UPDATE_RIDE_STATUS_ACTIVE : MailAction.UPDATE_RIDE_STATUS_INACTIVE;
                mailService.sendMail(user.getEmail(), action, rideMapper.toFindRideDetailResponse(ride, user));
            }
            return true;
        } catch (MessagingException | MailException | IOException e) {
            throw new CommonException(e);
        } catch (CommonException e) {
            throw e;
        }
    }

    @Override
    public RideDto findRideById(FindByIdRequest request) {
        return rideMapper.toDto(rideRepository.findById(request.getId()).orElse(null));
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
        Ride ride = rideRepository.findById(request.getId()).get();
        User user = userRepository.findById(ride.getUserId()).get();
        return rideMapper.toFindRideDetailResponse(ride, user);
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
}
