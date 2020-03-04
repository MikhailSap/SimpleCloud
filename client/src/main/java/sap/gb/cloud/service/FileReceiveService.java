package sap.gb.cloud.service;


import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FileReceiveService extends ReceiveService {
    private String localStorage;
    private byte[] bytes;
    private int fileNameLength;
    private String fileName;
    private long fileLength;

    public FileReceiveService(Socket connection) {
        super(connection);
        localStorage = "/Users/mihailmihail/my_cloud/SimpleCloud/storage/";
        try {
        fileNameLength = in.readInt();
        bytes = new byte[fileNameLength];
        in.read(bytes);
        fileName = new String(bytes);
            System.out.println(fileName);
        fileLength = in.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute() {
        int bufferSize = 1024;
        try (FileOutputStream fileOutputStream = new FileOutputStream(localStorage+fileName);
                BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream)){
            byte[] bytes = new byte[bufferSize];
        while (fileLength > 0){
            if (fileLength < bufferSize) {
                bytes = new byte[(int) fileLength];
                fileLength = 0;
            }
            in.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            fileLength -= bufferSize;
        }
            System.out.println("File was received");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
