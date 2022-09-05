package vn.rideshare;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import vn.rideshare.client.dto.ride.FindRideDetailResponse;
import vn.rideshare.client.dto.ride.UserDto;
import vn.rideshare.client.dto.ride.VehicleDto;
import vn.rideshare.common.MailAction;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.service.impl.MailServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Component
@SpringBootTest
public class TestSendMail {
    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Test
    void testSendMail() {
        User user = new User();
        user.setFullName("Phạm Trung Hiếu");
        user.setPhone("+84 975 799 155");
        user.setEmail("phamtrunghieu.dev@gmail.com");
        user.setDob(new Date());
        user.setPhotoURL("https://lh3.googleusercontent.com/a-/AFdZucqGaADi0X0O81rbWWNSXgqmiG5_ErNecZ7C5wVsbg=s96-c");
        user.setGender("male");
        assertDoesNotThrow(() -> this.mailServiceImpl.sendMail("phamtrunghieu.dev@gmail.com", MailAction.UPDATE_USER, user));
    }

    @Test
    void testSaveRide() throws IOException {
        FindRideDetailResponse response = new FindRideDetailResponse();
        List<String> criterions = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setEmail("phamtrunghieu.dev@gmail.com");
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setType("car");
        vehicleDto.setName("honda wave");
        vehicleDto.setLpn("98b99999");
        criterions.add("Không hút thuốc");
        criterions.add("Không thú cưng");
        criterions.add("Đi một mình");
        response.setStatus(EntityStatus.INACTIVE);
        response.setNote("test note");
        response.setCriterions(criterions);
        response.setDistance(999.99);
        response.setStartTime(LocalDateTime.now());
        response.setEndTime(LocalDateTime.now());
        response.setUser(userDto);
        response.setVehicle(vehicleDto);
        mailServiceImpl.sendMail(userDto.getEmail(), MailAction.CREATE_RIDE, response);
    }

    @Test
    void testConvertDistance() {
        double distance = 1234.2341423;
        System.out.println(Math.round(distance * 100.0 / 1000.0) / 100.0);
    }
}
