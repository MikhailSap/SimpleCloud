package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;


public class Delete extends ChangeService {
    private final Logger LOGGER = LogManager.getLogger();

    public Delete(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
    }

    @Override
    public boolean execute() {
        LOGGER.info("start delete file");
        if (file.delete()) {
            ctx.channel().writeAndFlush("File was deleted");
            LOGGER.info(fileName + " delete ok");
        } else {
            ctx.channel().writeAndFlush("File was not deleted");
            LOGGER.info(fileName + " delete fail");
        }
        return true;
    }
}
