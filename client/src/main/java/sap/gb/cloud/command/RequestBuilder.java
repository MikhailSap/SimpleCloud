package sap.gb.cloud.command;


import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class RequestBuilder {
    private String[] requestPreform;

    public RequestBuilder(String[] requestPreform) {
        this.requestPreform = requestPreform;
    }

    public ByteBuffer getRequest() {
        int requestLength = 0;
        int valueOfLengthPointer = 4;
        for (String s : requestPreform) {
            requestLength += valueOfLengthPointer + s.length();
        }
        ByteBuffer request = ByteBuffer.allocate(requestLength);
        try {
            for (String s : requestPreform) {
                request.putInt(s.length());
                request.put(s.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
