package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;

import java.io.File;

public class Rename extends ChangeService {
    private final Logger LOGGER = LogManager.getLogger();
    int newNameLength;
    String newName;

    public Rename(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
        newNameLength = byteBuf.readInt();
        bytes = new byte[newNameLength];
        byteBuf.readBytes(bytes);
        newName = new String(bytes);
    }

    @Override
    public boolean execute() {
        LOGGER.info("start rename file - " + fileName);
        if (file.renameTo(new File(userFolder.getValue() + newName))) {
            ctx.channel().writeAndFlush("File was renamed.");
            LOGGER.info(fileName + " was rename into - " + newName);
        } else {
            ctx.channel().writeAndFlush("File was not renamed");
            LOGGER.info(fileName + " was not renamed");
        }
        return true;
    }
}
