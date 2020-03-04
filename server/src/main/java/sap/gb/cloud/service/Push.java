package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Push extends FileService {
    private final Logger LOGGER = LogManager.getLogger();
    private long fileLength;
    private BufferedOutputStream outputStream;
    private int readableBytes;
    private int limiter;


    public Push(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
        fileLength = byteBuf.readLong();
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(userFolder.getValue() + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute() {
        LOGGER.info("start receive file - " + fileName);
        readableBytes = byteBuf.readableBytes();
        limiter = readableBytes > fileLength ? readableBytes - (int) fileLength : 0;
        try {
            fileLength -= readableBytes;
            System.out.println(fileLength);
            while (readableBytes > limiter) {
                outputStream.write(byteBuf.readByte());
                readableBytes--;
            }
            if (fileLength <= 0) {
            outputStream.close();
            ctx.channel().writeAndFlush("File was received");
            LOGGER.info(fileName + " was received");
            return true;
            }
        } catch (Exception e) {
            ctx.channel().writeAndFlush("File was not received");
            LOGGER.error(e);
            e.printStackTrace();
        }
        return false;
    }
}
