package vn.rideshare.model.route;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Lane{
    public List<String> indications;
    public boolean valid;
    public boolean active;
    public String valid_indication;
}
