package vn.rideshare.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.rideshare.client.dto.ride.FindRideDetailResponse;
import vn.rideshare.client.dto.ride.VehicleDto;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;
import vn.rideshare.model.User;
import vn.rideshare.model.Vehicle;
import vn.rideshare.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private static final int TOTAL_NUMBER_OF_USERS = 999;
    private static final String EMAIL_SUPPORT = "phamtrunghieu6d@gmail.com";
    private static final String URL_WEB = "http://localhost:4200";
    private static final String FROM = "phamtrunghieu.dev@outlook.com.vn";
    private static final String MOTORBIKE = "XE MÁY";
    private static final String CAR = "Ô TÔ";

    private static final String MALE = "Nam";

    private static final String FEMALE = "Nữ";

    public boolean sendMail(String to, MailAction action, Object data) throws MessagingException, MailException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(FROM);
        helper.setTo(to);
        helper.setSubject(action.getEmailTitle());
        helper.setText(buildContent(action, data), true);
//        javaMailSender.send(helper.getMimeMessage());
        return true;
    }

    private String buildContent(MailAction action, Object data) throws IOException {
        String result;
        if (action.toString().contains("USER")) {
            User user = User.class.cast(data);
            FileInputStream fis = new FileInputStream("src/main/resources/template-user.html");
            result = String.format(IOUtils.toString(fis, StandardCharsets.UTF_8),
                    action.getContentTitle(),
                    action.getContentSmallTitle(),
                    user.getPhotoURL(),
                    user.getFullName(),
                    convertGender(user.getGender()),
                    convertDob(user.getDob()),
                    convertStatus(user.getStatus()),
                    user.getEmail(),
                    user.getPhone(),
                    convertVehicle(user.getVehicles()),
                    URL_WEB,
                    TOTAL_NUMBER_OF_USERS,
                    EMAIL_SUPPORT
            );
        } else {
            Ride ride = Ride.class.cast(data);
            FileInputStream fis = new FileInputStream("src/main/resources/template-ride.html");
            result = String.format(IOUtils.toString(fis, StandardCharsets.UTF_8),
                    action.getContentTitle(),
                    action.getContentSmallTitle(),
                    convertStatus(ride.getStatus()),
                    "FAKE VEHICLE",
                    convertDistance(ride.getRoute().getDistance()),
                    convertLocalDateTime(ride.getStartTime()),
                    convertLocalDateTime(ride.getEndTime()),
                    convertCriterions(ride.getCriterions()),
                    ride.getNote(),
                    URL_WEB,
                    TOTAL_NUMBER_OF_USERS,
                    EMAIL_SUPPORT
            );
        }
        return result;
    }

    private double convertDistance(double distance) {
        return Math.round(distance * 100.0 / 1000.0) / 100.0;
    }

    private String convertVehicleType(String type) {
        return type.equals("car") ? CAR : MOTORBIKE;
    }

    private String convertVehicle(VehicleDto vehicleDto) {
        return String.format("%s - %s - %s",
                convertVehicleType(vehicleDto.getType()),
                vehicleDto.getName().toUpperCase(),
                vehicleDto.getLpn().toUpperCase()
        );
    }

    private String convertGender(String gender) {
        return gender.equals("male") ? MALE : FEMALE;
    }

    private String convertCriterions(List<String> criterions) {
        return String.join(", ", criterions);
    }

    private String convertVehicle(List<Vehicle> vehicles) {
        String result = "";
        for (Vehicle vehicle : vehicles) {
            result += convertType(vehicle.getType())
                    .concat(" - ")
                    .concat(vehicle.getName())
                    .concat(" - ")
                    .concat(vehicle.getLpn())
                    .concat("\n");
        }
        return result;
    }

    private String convertType(String type) {
        switch (type) {
            case "car": {
                type = CAR;
                break;
            }
            case "motorbike": {
                type = MOTORBIKE;
                break;
            }
            default: {
                type =MOTORBIKE;
            }
        }
        return type;
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
            case PENDING: {
                result = "Chờ phê duyệt";
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