package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;
import java.io.File;
import java.io.RandomAccessFile;


public class Get extends FileService {
    private final Logger LOGGER = LogManager.getLogger();
    private File file;
    private long fileLength;


    public Get(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
        file = new File(userFolder.getValue() + fileName);
        fileLength = file.length();
    }

    @Override
    public boolean execute() {
        LOGGER.info(fileName + " start send");
        if (!file.exists()) {
            ctx.channel().writeAndFlush("File is not exist");
            LOGGER.error(fileName + " is not exist");
        }
        int serviceInfoLength = 12;
        byteBuf = ctx.alloc().directBuffer(serviceInfoLength + fileNameLength);
        byteBuf.writeInt(fileNameLength);
        byteBuf.writeBytes(fileName.getBytes());
        byteBuf.writeLong(fileLength);
        ctx.write(byteBuf);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")){
            FileRegion fileRegion = new DefaultFileRegion(randomAccessFile.getChannel(), 0, fileLength);
            ctx.writeAndFlush(fileRegion);
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        LOGGER.info(fileName + " was sending to client");
        return true;
    }
}
