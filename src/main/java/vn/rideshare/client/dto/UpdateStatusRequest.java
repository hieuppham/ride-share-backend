package vn.rideshare.client.dto;

import lombok.Getter;
import lombok.Setter;
import vn.rideshare.model.EntityStatus;

@Getter
@Setter
public class UpdateStatusRequest {
    private String id;
    private EntityStatus status;
    private boolean sendEmail = true;
}
