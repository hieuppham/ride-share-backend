package vn.rideshare;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.rideshare.shared.CustomMongoTemplate;

@EnableMongoAuditing
@EnableScheduling
@SpringBootApplication
public class RideShareBackendApplication {
    @Value("${rt-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setPort(port);
        return new SocketIOServer(config);
    }

    @Bean("mongoTemplate")
    CustomMongoTemplate customMongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        return new CustomMongoTemplate(databaseFactory, converter);
    }

    public static void main(String[] args) {
        SpringApplication.run(RideShareBackendApplication.class, args);
    }

}
