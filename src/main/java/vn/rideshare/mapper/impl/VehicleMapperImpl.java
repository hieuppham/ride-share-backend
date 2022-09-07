package vn.rideshare.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.rideshare.client.dto.user.VehicleDto;
import vn.rideshare.mapper.VehicleMapper;
import vn.rideshare.model.Vehicle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapperImpl implements VehicleMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Vehicle toEntity(VehicleDto dto) {
       if (dto == null){
           return null;
       }
       return modelMapper.map(dto, Vehicle.class);
    }

    @Override
    public VehicleDto toDto(Vehicle entity) {
        if (entity == null){
            return null;
        }
        return modelMapper.map(entity, VehicleDto.class);
    }

    @Override
    public List<Vehicle> toEntity(List<VehicleDto> dtoList) {
        if (dtoList == null){
            return null;
        }
        return Arrays
                .stream(modelMapper
                        .map(dtoList, Vehicle[].class))
                .map(vehicle -> {
                    vehicle.setName(vehicle.getName().toUpperCase());
                    vehicle.setLpn(vehicle.getLpn().toUpperCase());
                    return vehicle;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> toDto(List<Vehicle> entityList) {
        return null;
    }
}
