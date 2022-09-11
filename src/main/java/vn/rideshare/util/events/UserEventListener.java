package vn.rideshare.util.events;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.service.MailService;

import java.io.IOException;
import java.util.Objects;

@Component
public class UserEventListener extends AbstractMongoEventListener<User> {

    @Autowired
    private MailService mailService;

    @Override
    public void onAfterSave(AfterSaveEvent<User> event) {
        try {
            User user = event.getSource();
            super.onAfterSave(event);
            if (Objects.equals(user.getStatus(), EntityStatus.PENDING) || Objects.equals(user.getStatus(), EntityStatus.INACTIVE)) {
                mailService.sendMail(user.getEmail(), MailAction.UPDATE_USER, user);
            }
        } catch (MessagingException | MailException | IOException e) {
            throw new CommonException(e);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }
}