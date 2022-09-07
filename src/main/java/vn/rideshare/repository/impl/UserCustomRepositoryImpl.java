package vn.rideshare.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserCustomRepository;

import java.util.List;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> findUsersByText(String text) {
        CriteriaDefinition searchCriteria  = TextCriteria.forDefaultLanguage()
                .caseSensitive(false)
                .matching(text);
        CriteriaDefinition active = Criteria.where("status").is("ACTIVE");
        return this.mongoTemplate.find( new Query(searchCriteria).addCriteria(active), User.class);
    }
}
