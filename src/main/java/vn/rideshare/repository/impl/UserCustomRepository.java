package vn.rideshare.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.rideshare.model.User;

import java.util.List;

@Service
public class UserCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public User findOneByUID(String uid){
     return  this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(uid)),User.class);
    }

    public boolean existsByUID(String uid) {
        return this.mongoTemplate.exists(Query.query(Criteria.where("uid").is(uid)), User.class);
    }

    public User findAndModify(User user){
      return this.mongoTemplate
              .findAndModify(Query.query(Criteria.where("uid").is(user.getUid())),
                      Update.update("fullName", user.getFullName())
                              .set("dob", user.getDob())
                              .set("gender", user.getGender())
                              .set("phone", user.getPhone())
                              .set("vehicles", user.getVehicles()), User.class);

    }

    public List<User> searchUser(String what) {
        CriteriaDefinition searchCriteria  = TextCriteria.forDefaultLanguage()
                .caseSensitive(false)
                .matching(what);
        CriteriaDefinition active = Criteria.where("status").is("ACTIVE");
        return this.mongoTemplate.find( new Query(searchCriteria).addCriteria(active), User.class);
    }
}
