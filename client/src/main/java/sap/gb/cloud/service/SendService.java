package sap.gb.cloud.service;

import sap.gb.cloud.command.RequestBuilder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class SendService implements Service{
    protected DataOutputStream out;
    protected RequestBuilder requestBuilder;

    public SendService(Socket connection, RequestBuilder requestBuilder) {
        try {
            this.out = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.requestBuilder = requestBuilder;
    }

    public abstract boolean execute();
}
