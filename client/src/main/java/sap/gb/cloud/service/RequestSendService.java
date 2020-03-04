package sap.gb.cloud.service;


import sap.gb.cloud.command.RequestBuilder;

import java.net.Socket;
import java.nio.ByteBuffer;


public class RequestSendService extends SendService {

    public RequestSendService(Socket connection, RequestBuilder requestBuilder) {
        super(connection, requestBuilder);
    }

    @Override
    public boolean execute() {
        ByteBuffer request = requestBuilder.getRequest();
        try {
            out.write(request.array());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
