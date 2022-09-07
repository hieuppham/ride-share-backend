package vn.rideshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import vn.rideshare.controller.EngineIoHandler;

@Configuration
@EnableWebSocket
public class EngineIoConfigurator implements WebSocketConfigurer {

    private final EngineIoHandler mEngineIoHandler;

    public EngineIoConfigurator(EngineIoHandler engineIoHandler) {
        mEngineIoHandler = engineIoHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mEngineIoHandler, "/engine.io/")
                .addInterceptors(mEngineIoHandler);
    }


}