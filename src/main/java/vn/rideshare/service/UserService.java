package vn.rideshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.rideshare.dto.RequestUpdateStatusDto;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserRepository;
import vn.rideshare.repository.impl.UserCustomRepository;

import java.util.List;

@Repository
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCustomRepository userCustomRepository;

    public ResponseEntity<User> upsertUser(User user) {
        User saveUser;
        if (this.userCustomRepository.existsByUID(user.getUid())) {
            saveUser = this.userCustomRepository.findAndModify(user);
        } else {
            saveUser = this.userRepository.save(user);
        }
        return ResponseEntity.ok().body(saveUser);
    }

    public ResponseEntity<Boolean> updateStatus(RequestUpdateStatusDto request) {
        this.userRepository.updateStatusById(request.getId(), request.getStatus());
        return ResponseEntity.ok()
                .body(true);
    }

    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok()
                .body(this.userRepository.findAll());
    }

    public ResponseEntity<User> getUserByUID(String uid) {
        return ResponseEntity.ok()
                .body(this.userCustomRepository.findOneByUID(uid));
    }

    public ResponseEntity<List<User>> searchUser(String what) {
        return ResponseEntity.ok()
                .body(this.userCustomRepository.searchUser(what));
    }
}
