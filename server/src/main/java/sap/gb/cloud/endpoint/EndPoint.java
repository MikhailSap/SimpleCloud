package sap.gb.cloud.endpoint;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EndPoint {
    private final int PORT = 8993;
    private DataInputStream incoming;
    private DataOutputStream outgoing;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket connection = serverSocket.accept();
            incoming = new DataInputStream(connection.getInputStream());
            outgoing = new DataOutputStream(connection.getOutputStream());
            OutputStream os = new FileOutputStream("/Users/mihailmihail/Documents/loool.txt");
            byte [] bytes = incoming.readAllBytes();
            os.write(bytes); // пока без парсинга
            System.out.println(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
