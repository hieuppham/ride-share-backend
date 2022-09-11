package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Lane{
    private List<String> indications;
    private boolean valid;
    private boolean active;
    private String valid_indication;
}
