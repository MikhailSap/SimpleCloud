package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import sap.gb.cloud.entity.UserFolder;

public abstract class BaseService implements Service{
    protected ChannelHandlerContext ctx;
    protected ByteBuf byteBuf;
    protected UserFolder userFolder;

    public BaseService(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        this.ctx = ctx;
        this.byteBuf = byteBuf;
        this.userFolder = userFolder;
    }

    abstract public boolean execute();
}
