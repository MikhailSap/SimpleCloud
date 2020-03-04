package sap.gb.cloud.service;


import java.io.DataInputStream;
import java.net.Socket;

public abstract class ReceiveService implements Service {
    protected DataInputStream in;

    public ReceiveService(Socket connection) {
        try {
            this.in = new DataInputStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract boolean execute();
}
