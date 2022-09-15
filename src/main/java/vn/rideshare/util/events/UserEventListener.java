package vn.rideshare.util.events;


import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.service.MailService;

import java.util.Objects;

@Component
public class UserEventListener extends AbstractMongoEventListener<User> {
    @Autowired
    private MailService mailService;

    @Override
    public void onAfterConvert(AfterConvertEvent<User> event) {
        super.onAfterConvert(event);
        event.getSource().savedState = SerializationUtils.clone(event.getSource().getStatus());
    }

    @Override
    public void onAfterSave(AfterSaveEvent<User> event) {
        try {
            User user = event.getSource();
            super.onAfterSave(event);
            if (!Objects.equals(user.getStatus(), EntityStatus.UNKNOWN)) {
                mailService.sendMail(MailAction.UPDATE_USER, user);
            }
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }
}
