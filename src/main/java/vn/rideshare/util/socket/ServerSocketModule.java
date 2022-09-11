package vn.rideshare.util.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.rideshare.model.EntityStatus;

@Component
public class ServerSocketModule {

    private final SocketIONamespace namespace;

    @Autowired
    public ServerSocketModule(SocketIOServer server) {
        namespace = server.addNamespace("/ride-share");
        namespace.addConnectListener(onConnected());
        namespace.addDisconnectListener(onDisconnected());
        namespace.addEventListener(SocketEvent.DATA, SocketMessage.class, onDataReceived());
    }

    public boolean sendMessage(String event, SocketMessage message) {
        namespace.getBroadcastOperations().sendEvent(event, message);
        return true;
    }

    private DataListener<SocketMessage> onDataReceived() {
        return (client, data, ackSender) ->
                namespace.getBroadcastOperations().sendEvent(SocketEvent.DATA, data);

    }

    private ConnectListener onConnected() {
        return client -> namespace.getBroadcastOperations().sendEvent(
                SocketEvent.NEW_CONNECT,
                new ConnectInfo(client.getSessionId().toString(),
                        namespace.getBroadcastOperations().getClients().size()));
    }

    private DisconnectListener onDisconnected() {
        return client ->
                namespace.getBroadcastOperations().sendEvent(SocketEvent.NEW_DISCONNECT,
                        new ConnectInfo(client.getSessionId().toString(),
                                namespace.getBroadcastOperations().getClients().size()));
    }
}
