package vn.rideshare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Service;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.Ride;

import java.util.List;

@Service
public interface RideRepository extends MongoRepository<Ride, String>, RideCustomRepository {
    @Query(value = "{_id: ?0}")
    @Update(value = "{$set: {status: ?1}}")
    void updateStatus(String id, EntityStatus status);
}
