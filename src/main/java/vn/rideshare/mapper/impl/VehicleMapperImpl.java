package vn.rideshare.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.rideshare.client.dto.user.VehicleDto;
import vn.rideshare.mapper.VehicleMapper;
import vn.rideshare.model.Vehicle;

import java.util.List;

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
        return null;
    }

    @Override
    public List<VehicleDto> toDto(List<Vehicle> entityList) {
        return null;
    }
}
