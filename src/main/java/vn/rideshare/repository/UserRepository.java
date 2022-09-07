package vn.rideshare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserCustomRepository {
    @Query(value = "{_id: ?0}")
    @Update(update = "{$set: {status: ?1}}")
    void updateStatusById(String id, EntityStatus status);

    Optional<User> findUserByUid(String uid);
}