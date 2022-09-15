package vn.rideshare.util.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import vn.rideshare.common.CommonException;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.User;
import vn.rideshare.service.MailService;
import vn.rideshare.shared.AfterFindAndModifyEvent;
import vn.rideshare.shared.FindAndModifyEventListener;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class UserFindAndModifyEventListener extends FindAndModifyEventListener<User> {
    @Autowired
    private MailService mailService;

    @Override
    public void onAfterFindAndModify(AfterFindAndModifyEvent<User> event) {
        try {
            super.onAfterFindAndModify(event);
            User user = event.getSource();
            mailService.sendMail(MailAction.UPDATE_USER, user);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }
}
