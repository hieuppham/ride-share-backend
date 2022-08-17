package vn.rideshare.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BoundRequestDto {
    private List<Object> bottomLeft;
    private List<Object> upperRight;
}
