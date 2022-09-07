package vn.rideshare.repository;

import org.springframework.stereotype.Repository;
import vn.rideshare.model.User;

import java.util.List;

@Repository
public interface UserCustomRepository {
    List<User> findUsersByText(String text);
}
