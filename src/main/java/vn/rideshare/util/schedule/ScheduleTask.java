package vn.rideshare.util.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.rideshare.repository.RideRepository;
import vn.rideshare.repository.UserRepository;

@Component
public class ScheduleTask {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    /**
     * check status == PENDING and lastUpdateTime + 10mins <= currentTime
     */
    @Scheduled(fixedRate = 10000)
    public void activateUser() {
        userRepository.findAndActivate();
    }

    /**
     * check status == PENDING and lastUpdateTime + 10mins <= currentTime
     */
    @Scheduled(fixedRate = 10000)
    public void activateRide() {
        rideRepository.findAndActivate();
    }

    /**
     * check status == ACTIVE and endTime <= currentTime
     */
    @Scheduled(fixedRate = 10000)
    public void inactiveRide() {
        rideRepository.findAndInactivate();
    }
}
