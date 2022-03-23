package sap.gb.cloud.service;


import sap.gb.cloud.command.RequestBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;


public class FileSendService extends SendService {
    private File file;
    private long fileLength;
    private String[] requestPreform;

    public FileSendService(Socket connection, RequestBuilder requestBuilder) {
        super(connection, requestBuilder);
        int indexOfPath = 1;
        requestPreform = requestBuilder.getRequestPreform();
        file = new File(requestPreform[indexOfPath]);
        fileLength = file.length();
        requestPreform[indexOfPath] = file.getName();
    }

    @Override
    public boolean execute() {
        ByteBuffer request;
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            request = requestBuilder.getRequest();
            out.write(request.array());
            out.writeLong(fileLength);
            int bufferSize = 1024;
            byte[] bytes = new byte[bufferSize];
            while (fileInputStream.available() > 0) {
            fileInputStream.read(bytes);
            out.write(bytes);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
