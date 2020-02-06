package sap.gb.cloud.connect;

import java.io.IOException;
import java.net.Socket;


public class Connector {
    private final int PORT = 8993;
    private String serverIp = "localhost";

    public Socket getConnection() throws IOException {
        return new Socket(serverIp, PORT);
    }
}
