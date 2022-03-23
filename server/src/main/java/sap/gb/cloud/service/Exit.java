package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;

public class Exit extends BaseService {
    private final Logger LOGGER = LogManager.getLogger();

    public Exit(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
    }

    @Override
    public boolean execute() {
        LOGGER.info("exit");
        ctx.channel().writeAndFlush("Exit ok.");
        ctx.close();
        return true;
    }
}
