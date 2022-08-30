package vn.rideshare.service;

import vn.rideshare.common.MailAction;

import java.io.IOException;

public interface MailService {
    boolean sendMail(String to, MailAction action, Object data) throws RuntimeException,  IOException;
}
