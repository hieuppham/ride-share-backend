package vn.rideshare.util.socket;

public final class SocketEvent {
    private SocketEvent() {
    }

    public static final String DATA = "data";
    public static final String NEW_CONNECT = "new-connect";
    public static final String NEW_DISCONNECT = "new-disconnect";

    public static final String RIDE_ACTIVATED = "ride-activated";
    public static final String RIDE_PREPARED = "ride-prepared";
    public static final String RIDE_EXPIRED = "ride-expired";
    public static final String RIDE_INACTIVATED = "ride-inactivated";
    public static final String RIDE_DISABLED = "ride-disabled";

    public static final String USER_ACTIVATED = "user-activated";
    public static final String USER_DEACTIVATED = "user-deactivated";
    public static final String NOTHING = "nothing";
}
