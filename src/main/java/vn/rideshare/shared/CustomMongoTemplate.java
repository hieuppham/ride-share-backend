package vn.rideshare.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

public class CustomMongoTemplate extends MongoTemplate {
    private ApplicationEventPublisher applicationEventPublisher;

    public CustomMongoTemplate(MongoDatabaseFactory mongoDbFactory, MappingMongoConverter converter) {
        super(mongoDbFactory, converter);
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public <T> T findAndModify(Query query, UpdateDefinition update, Class<T> entityClass) {
        T result = super.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), entityClass);
        if (result != null) {
            this.applicationEventPublisher.publishEvent(new AfterFindAndModifyEvent<>(result));
        }
        return result;
    }
}
