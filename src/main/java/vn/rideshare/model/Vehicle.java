package vn.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String type;
    private String name;
    private String licensePlateNumber;
}
