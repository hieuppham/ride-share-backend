package vn.rideshare.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import vn.rideshare.model.EntityStatus;
import vn.rideshare.model.User;
import vn.rideshare.repository.UserCustomRepository;

import java.time.Instant;
import java.util.List;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private static final String STATUS = "status";
    private static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    private static final long PENDING_TIME = 600l;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> findUsersByText(String text) {
        CriteriaDefinition searchCriteria = TextCriteria.forDefaultLanguage()
                .caseSensitive(false)
                .matching(text);
        CriteriaDefinition active = Criteria.where(STATUS).is(EntityStatus.ACTIVE);
        return this.mongoTemplate.find(new Query(searchCriteria).addCriteria(active), User.class);
    }

    @Override
    public void findAndActivate() {
        Query query = Query.query(Criteria.where(STATUS).is(EntityStatus.PENDING)
                .and(LAST_MODIFIED_DATE).lte(Instant.now().minusSeconds(PENDING_TIME)));
        UpdateDefinition update = Update.update(STATUS, EntityStatus.ACTIVE).set(LAST_MODIFIED_DATE, Instant.now());
        mongoTemplate.findAndModify(query, update, User.class);
    }
}
