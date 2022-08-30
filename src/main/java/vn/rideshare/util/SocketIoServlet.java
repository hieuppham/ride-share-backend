package vn.rideshare.util;

import io.socket.engineio.server.Emitter;
import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoSocket;
import io.socket.socketio.server.SocketIoServer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/socket.io/*")
public class SocketIoServlet extends HttpServlet {
    private static final EngineIoServer mEngineIoServer = new EngineIoServer();
    private static final SocketIoServer mSocketIoServer = new SocketIoServer(mEngineIoServer);

    static {
        mEngineIoServer.on("connection", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                EngineIoSocket socket = (EngineIoSocket) args[0];
                System.out.println(socket.getId());
            }
        });

        mEngineIoServer.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Object message = args[0];
                System.out.println(message);
            }
        });
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mEngineIoServer.handleRequest(request, response);


    }
}
