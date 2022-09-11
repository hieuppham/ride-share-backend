package vn.rideshare.util.events;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class CloseApplicationListner implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private SocketIOServer server;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
      server.stop();
    }
}
