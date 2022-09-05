package vn.rideshare.config;

import io.socket.engineio.server.EngineIoServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineIoServerBean {
    @Bean
    public EngineIoServer engineIoServer() {
        return new EngineIoServer();
    }
}
