package vn.rideshare.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.ride.FindRideDetailResponse;
import vn.rideshare.client.dto.ride.VehicleDto;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private static final int FAKE_TOTAL_NUMBER_OF_USERS = 999;
    private static final String FAKE_EMAIL_SUPPORT = "phamtrunghieu6d@gmail.com";
    private static final String FAKE_URL_WEB = "http://localhost:4200";

    public boolean sendMail(String to, MailAction action, Object data) throws RuntimeException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setFrom("phamtrunghieu.dev@outlook.com.vn");
            helper.setTo(to);
            helper.setSubject(action.getEmailTitle());
            helper.setText(buildContent(action, data), true);
            javaMailSender.send(helper.getMimeMessage());
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildContent(MailAction action, Object data) throws IOException {
        String result;
        if (action.toString().contains("USER")) {
            User user = User.class.cast(data);
            FileInputStream fis = new FileInputStream("src/main/resources/template-user.html");
            result = String.format(IOUtils.toString(fis, "UTF-8"),
                    action.getContentTitle(),
                    action.getContentSmallTitle(),
                    user.getPhotoURL(),
                    user.getFullName(),
                    convertGender(user.getGender()),
                    convertDob(user.getDob()),
                    convertStatus(user.getStatus()),
                    user.getEmail(),
                    user.getPhone(),
                    "XE MÁY - HONDA WAVE - 98B98888",
                    FAKE_URL_WEB,
                    FAKE_TOTAL_NUMBER_OF_USERS,
                    FAKE_EMAIL_SUPPORT
            );
        } else {
            FindRideDetailResponse ride = FindRideDetailResponse.class.cast(data);
            FileInputStream fis = new FileInputStream("src/main/resources/template-ride.html");
            result = String.format(IOUtils.toString(fis, "UTF-8"),
                    action.getContentTitle(),
                    action.getContentSmallTitle(),
                    convertStatus(ride.getStatus()),
                    convertVehicle(ride.getVehicle()),
                    ride.getDistance(),
                    convertLocalDateTime(ride.getStartTime()),
                    convertLocalDateTime(ride.getEndTime()),
                    convertCriterions(ride.getCriterions()),
                    ride.getNote(),
                    FAKE_URL_WEB,
                    FAKE_TOTAL_NUMBER_OF_USERS,
                    FAKE_EMAIL_SUPPORT
            );
        }
        return result;
    }

    private String convertVehicleType(String type) {
        return type.equals("car") ? "Ô TÔ" : "XE MÁY";
    }

    private String convertVehicle(VehicleDto vehicleDto) {
        return String.format("%s - %s - %s",
                convertVehicleType(vehicleDto.getType()),
                vehicleDto.getName().toUpperCase(),
                vehicleDto.getLpn().toUpperCase()
        );
    }

    private String convertGender(String gender) {
        return gender.equals("male") ? "Nam" : "Nữ";
    }

    private String convertCriterions(List criterions) {
        return String.join(", ", criterions);
    }

    private String convertStatus(EntityStatus status) {
        String result;
        switch (status) {
            case ACTIVE: {
                result = "Hoạt động";
                break;
            }
            case INACTIVE: {
                result = "Không hoạt động";
                break;
            }
            case UNKNOWN: {
                result = "Chưa kích hoạt";
                break;
            }
            case EXPIRED: {
                result = "Đã kết thúc";
                break;

            }
            default: {
                result = "Không hoạt động";
                break;
            }
        }
        return result;
    }

    private String convertDob(Date dob) {
        return String.format("%s/%s/%s", dob.getDate(), dob.getMonth() + 1, dob.getYear() + 1900);
    }

    private String convertLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
    }
}