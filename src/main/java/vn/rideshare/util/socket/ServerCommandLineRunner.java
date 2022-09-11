package vn.rideshare.util.socket;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.rideshare.common.CommonException;

@Component
public class ServerCommandLineRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public ServerCommandLineRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) {
        try {
            server.start();
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

}