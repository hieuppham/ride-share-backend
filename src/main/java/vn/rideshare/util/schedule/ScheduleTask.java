package vn.rideshare.util.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.rideshare.repository.RideRepository;
import vn.rideshare.repository.UserRepository;

@Component
public class ScheduleTask {
    private static final long FIXED_RATE = 1L;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RideRepository rideRepository;

    /**
     * check status == PENDING and lastUpdateTime + 10mins <= currentTime
     */
    @Scheduled(fixedRate = FIXED_RATE)
    public void activateUser() {
        userRepository.findAndActivate();
    }

    /**
     * check status == PENDING and lastModifiedAt + 10mins <= currentTime
     */
    @Scheduled(fixedRate = FIXED_RATE)
    public void prepareRide() {
        rideRepository.findAndPrepare();
    }

    /**
     * check status == PREPARE and startTime <= currentTime
     */
    @Scheduled(fixedRate = FIXED_RATE)
    public void activateRide() {
        rideRepository.findAndActivate();
    }

    /**
     * check status == ACTIVE and endTime <= currentTime
     */
    @Scheduled(fixedRate = FIXED_RATE)
    public void expireRide() {
        rideRepository.findAndExpire();
    }

    @Scheduled(fixedRate = FIXED_RATE)
    public void disableRide() {
        rideRepository.findAndDisable();
    }
}
