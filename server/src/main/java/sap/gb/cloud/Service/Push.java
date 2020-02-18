package sap.gb.cloud.Service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.nio.AbstractNioByteChannel;
import sap.gb.cloud.handler.ClientCommandHandler;

import java.io.FileOutputStream;
import java.io.IOException;

public class Push implements Service {
    private int fileNameLength;
    private String fileName;
    private long fileLength;
    private byte[] bytes;
    private String clientStorage = "storage/";
    private FileOutputStream outputStream;
    private boolean inProcess;
    //private byte[] container = new byte[35536];
    //private int staticLength = 3946367;

    @Override
    public String execute(ByteBuf byteBuf) {
        if (!inProcess) {
            fileNameLength = byteBuf.readInt();
            System.out.println("fileNameLength " + fileNameLength);
            bytes = new byte[fileNameLength];
            byteBuf.readBytes(bytes);
            fileName = new String(bytes);
            System.out.println("fileName " + fileName);
            fileLength = byteBuf.readLong();
            System.out.println("fileLength " + fileLength);
            try {
                outputStream = new FileOutputStream(clientStorage+fileName);
            } catch (IOException e) {

            }
            inProcess = true;
        }
        System.out.println("Execute");
        int readableBytes = byteBuf.readableBytes();

        //Попытка оптимизации, пока в работе...

//        try {
//            System.out.println(readableBytes);
//
//            while (readableBytes > 0 && fileLength >=0) {
//                if (readableBytes > fileLength) {
//                    byteBuf.getBytes(0, outputStream, (int) fileLength);
//                } else {
//                    byteBuf.getBytes(0, outputStream, readableBytes);
//                }
//                byteBuf.clear();
//
//                //byteBuf.readBytes(container);
////                if (readableBytes > fileLength)
////                    outputStream.write(container, 0, (int) fileLength);
////                else
////                outputStream.write(container, 0, readableBytes);
//                fileLength -= readableBytes;
//                System.out.println("l" + fileLength);
//            }
//        } catch (Exception e) {
//
//            //e.printStackTrace();
//
//        }

        try {
            System.out.println(readableBytes);

            while (readableBytes > 0 && fileLength >=0) {
                if (readableBytes > fileLength)
                    bytes = new byte[(int) fileLength];
                else
                    bytes = new byte[readableBytes];
                byteBuf.readBytes(bytes);
                outputStream.write(bytes);
                fileLength -= readableBytes;
                System.out.println("l" + fileLength);
            }
        } catch (Exception e) {
            // файл пишет нормально но почемуто хватает эксепшн
            //e.printStackTrace();
        }
        System.out.println("L" + fileLength);
        if (fileLength <= 0)
        return "File has been saved.";
        return "inProcess";
    }
}
