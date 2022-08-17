package vn.rideshare.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class RideShareSocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        Socket socket = serverSocket.accept();
        DataInputStream din = new DataInputStream(socket.getInputStream());
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        String strIn = "", strOut = "";
        while (!strIn.equals("stop")){
            strIn = din.readUTF();
            System.out.println("Client: " + strIn);
//            strOut = System.in.
        }
    }
}
