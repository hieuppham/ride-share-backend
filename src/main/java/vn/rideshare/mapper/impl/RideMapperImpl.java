package vn.rideshare.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.rideshare.client.dto.ride.*;
import vn.rideshare.mapper.RideMapper;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RideMapperImpl implements RideMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Ride toEntity(RideDto dto) {
        return modelMapper.map(dto, Ride.class);
    }

    @Override
    public RideDto toDto(Ride entity) {

        return modelMapper.map(entity, RideDto.class);
    }

    @Override
    public List<Ride> toEntity(List<RideDto> dtoList) {
        return null;
    }

    @Override
    public List<RideDto> toDto(List<Ride> entityList) {
        return Arrays.asList(modelMapper.map(entityList, RideDto[].class));
    }

    @Override
    public List<FindRidesDetailResponse> toFindRidesByBoundResponse(List<Ride> entityList) {
        return null;
    }

    @Override
    public Ride toEntity(SaveRideRequest request) {
        return modelMapper.map(request, Ride.class);
    }

    @Override
    public List<FindRidesAdminResponse> toFindRidesAdminResponse(List<Ride> entityList) {
        List<FindRidesAdminResponse> rides = entityList.stream().map(ride -> {
            FindRidesAdminResponse response = new FindRidesAdminResponse();
            response.setId(ride.getId());
            response.setStartPointTitle(ride.getPath().getProperties().getStartPointTitle());
            response.setEndPointTitle(ride.getPath().getProperties().getEndPointTitle());
            response.setStartTime(ride.getStartTime());
            response.setEndTime(ride.getEndTime());
            response.setStatus(ride.getStatus());
            response.setDistance(ride.getRoute().getDistance());
            return response;
        }).collect(Collectors.toList());
        return rides;
    }

    @Override
    public List<FindRidesByUserIdResponse> toFindRidesByUidResponse(List<Ride> entityList) {
        List<FindRidesByUserIdResponse> rides = entityList.stream().map(ride -> {
            FindRidesByUserIdResponse response = new FindRidesByUserIdResponse();
            response.setId(ride.getId());
            response.setStartTime(ride.getStartTime());
            response.setEndTime(ride.getEndTime());
            response.setCriterions(ride.getCriterions());
            response.setNote(ride.getNote());
            response.setStatus(ride.getStatus());
            return response;
        }).collect(Collectors.toList());
        return rides;
    }

    @Override
    public FindRideDetailResponse toFindRideDetailResponse(Ride ride, User user) {
        FindRideDetailResponse response = new FindRideDetailResponse();
        UserDto userDto = new UserDto();
        VehicleDto vehicleDto;
        response.setId(ride.getId());
        response.setStartTime(ride.getStartTime());
        response.setEndTime(ride.getEndTime());
        response.setDistance(ride.getRoute().getDistance());
        response.setNote(ride.getNote());
        response.setCriterions(ride.getCriterions());
        response.setStatus(ride.getStatus());
        response.setStartPointTitle(ride.getPath().getProperties().getStartPointTitle());
        response.setEndPointTitle(ride.getPath().getProperties().getEndPointTitle());
        userDto.setId(user.getId());
        userDto.setPhotoURL(user.getPhotoURL());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setGender(user.getGender());
        response.setUser(userDto);
        vehicleDto = user.getVehicles().stream().filter(v ->
                v.getId() == ride.getVehicleId()
        ).map(v -> {
            VehicleDto var = new VehicleDto();
            var.setId(v.getId());
            var.setType(v.getType());
            var.setLpn(v.getLpn());
            var.setName(v.getName());
            return var;
        }).findFirst().get();
        response.setVehicle(vehicleDto);
        return response;
    }
}
