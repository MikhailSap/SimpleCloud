package sap.gb.cloud.net;

import java.net.Socket;

public class Connector {
    private Socket connection;

    public Socket getConnection(String serverIp, int port) {
        try {
            connection = new Socket(serverIp, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
