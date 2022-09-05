package vn.rideshare.controller;

import io.socket.engineio.server.Emitter;
import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoSocket;
import org.springframework.stereotype.Component;

@Component
public class SocketHandler {
    private EngineIoServer mEngineIoServer;

    public SocketHandler(EngineIoServer mEngineIoServer){
        this.mEngineIoServer  = mEngineIoServer;
        this.mEngineIoServer.on("connection", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                EngineIoSocket socket = (EngineIoSocket) objects[0];
                System.out.println(socket);
            }
        });

        this.mEngineIoServer.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Object message = args[0];
                // message can be either String or byte[]
                // Do something with message.
            }
        });
    }
}
