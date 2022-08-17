package vn.rideshare.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.rideshare.model.EntityStatus;

@Getter
@NoArgsConstructor
public class RequestUpdateStatusDto {
    private String id;
    private EntityStatus status;
}
