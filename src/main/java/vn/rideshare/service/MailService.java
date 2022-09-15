package vn.rideshare.service;

import org.springframework.mail.MailException;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {
    boolean sendMail(MailAction action, User user) throws MessagingException, MailException,  IOException;
    boolean sendMail(MailAction action, Ride ride, User user) throws MessagingException, MailException, IOException;
}
