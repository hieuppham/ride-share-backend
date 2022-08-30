package vn.rideshare.client.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
    private int id;
    private String type;
    private String name;
    private String lpn;
    private String image;
    private String lpnImage;
}
