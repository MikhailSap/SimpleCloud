package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import sap.gb.cloud.entity.UserFolder;

import java.io.File;

public abstract class ChangeService extends FileService {
    protected File file;

    public ChangeService(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
        file = new File(userFolder.getValue() + fileName);
    }
}
