package vn.rideshare.util.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConnectInfo {
    private String sessionId;
    private int numberOfConnection;
}
