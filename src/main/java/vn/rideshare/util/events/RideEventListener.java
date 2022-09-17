package vn.rideshare.util.events;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.stereotype.Component;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.MailAction;
import vn.rideshare.common.ResponseCode;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.service.MailService;
import vn.rideshare.util.socket.ServerSocketModule;
import vn.rideshare.util.socket.SocketEvent;
import vn.rideshare.util.socket.SocketMessage;

@Component
public class RideEventListener extends AbstractMongoEventListener<Ride> {
    private EntityStatus oldStatus;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServerSocketModule socketModule;

    @Override
    public void onAfterConvert(AfterConvertEvent<Ride> event) {
        super.onAfterConvert(event);
        event.getSource().savedState = SerializationUtils.clone(event.getSource().getStatus());
    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<Ride> event) {
        super.onBeforeSave(event);
        oldStatus = event.getSource().getSavedState();
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Ride> event) {
        try {
            super.onAfterSave(event);
            Ride ride = event.getSource();
            SocketMessage message = new SocketMessage(ride.getId(), ride.getStatus().toString());
            socketModule.sendMessage(getEvent(oldStatus, ride.getStatus()), message);
            User user = userRepository.findById(ride.getUserId()).orElseThrow(() -> new CommonException(ResponseCode.NOT_FOUND));
            mailService.sendMail(MailAction.UPDATE_RIDE, ride, user);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    private String getEvent(EntityStatus oldStatus, EntityStatus newStatus) {
        String event = SocketEvent.NOTHING;
        if ((oldStatus == EntityStatus.INACTIVE || oldStatus == EntityStatus.PENDING)
                && newStatus == EntityStatus.PREPARE) {
            event = SocketEvent.RIDE_PREPARED;
        } else if ((oldStatus == EntityStatus.INACTIVE || oldStatus == EntityStatus.PENDING)
                && newStatus == EntityStatus.ACTIVE) {
            event = SocketEvent.RIDE_ACTIVATED;
        } else if (oldStatus == EntityStatus.ACTIVE &&
                (newStatus == EntityStatus.INACTIVE || newStatus == EntityStatus.PENDING)) {
            event = SocketEvent.RIDE_INACTIVATED;
        }else if (newStatus == EntityStatus.PENDING){
            event = SocketEvent.RIDE_INACTIVATED;
        }
        return event;
    }
}
