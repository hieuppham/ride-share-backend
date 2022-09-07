package vn.rideshare.service;

import org.springframework.mail.MailException;
import vn.rideshare.common.MailAction;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {
    boolean sendMail(String to, MailAction action, Object data) throws MessagingException, MailException,  IOException;
}
