package vn.rideshare.client.dto.ride;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
    private int id;
    private String type;
    private String name;
    private String lpn;
}
