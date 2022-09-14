package vn.rideshare.util.socket;

public final class SocketEvent {
    private SocketEvent(){}

    public static final String DATA = "data";
    public static final String NEW_CONNECT = "new-connect";
    public static final String NEW_DISCONNECT = "new-disconnect";
    public static final String RIDE_ADDED = "ride-added";
    public static final String RIDE_REMOVED = "ride-removed";
    public static final String USER_ACTIVATED = "user-activated";
    public static final String USER_DEACTIVATED = "user-deactivated";
    public static final String NOTHING = "nothing";
}
