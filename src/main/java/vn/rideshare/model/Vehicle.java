package vn.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private int id;
    private String type;
    private String name;
    private String lpn;
    private String image;
    private String lpnImage;
}
