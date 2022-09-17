package vn.rideshare.util.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.MailAction;
import vn.rideshare.common.ResponseCode;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.MailService;
import vn.rideshare.shared.AfterFindAndModifyEvent;
import vn.rideshare.shared.FindAndModifyEventListener;
import vn.rideshare.util.socket.ServerSocketModule;
import vn.rideshare.util.socket.SocketEvent;
import vn.rideshare.util.socket.SocketMessage;

@Component
public class RideFindAndModifyEventListener extends FindAndModifyEventListener<Ride> {
    @Autowired
    private ServerSocketModule socketModule;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Override
    public void onAfterFindAndModify(AfterFindAndModifyEvent<Ride> event) {
        try {
            super.onAfterFindAndModify(event);
            Ride ride = event.getSource();
            SocketMessage message = new SocketMessage(ride.getId(), ride.getStatus().toString());
            socketModule.sendMessage(getEvent(ride.getStatus()), message);
            User user = userRepository.findById(ride.getUserId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            mailService.sendMail(MailAction.UPDATE_RIDE, ride, user);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    private String getEvent(EntityStatus status) {
        String result;
        if (status == EntityStatus.ACTIVE) {
            result = SocketEvent.RIDE_ACTIVATED;
        } else if (status == EntityStatus.PREPARE) {
            result = SocketEvent.RIDE_PREPARED;
        } else if (status == EntityStatus.INACTIVE) {
            result = SocketEvent.RIDE_INACTIVATED;
        } else if (status == EntityStatus.EXPIRED) {
            result = SocketEvent.RIDE_EXPIRED;
        } else if (status == EntityStatus.DISABLE) {
            result = SocketEvent.RIDE_DISABLED;
        } else {
            result = SocketEvent.NOTHING;
        }
        return result;
    }
}
