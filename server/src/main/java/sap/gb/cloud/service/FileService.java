package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import sap.gb.cloud.entity.UserFolder;

public abstract class FileService extends BaseService{
    protected int fileNameLength;
    protected String fileName;
    protected byte[] bytes;

    FileService(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
        fileNameLength = byteBuf.readInt();
        bytes = new byte[fileNameLength];
        byteBuf.readBytes(bytes);
        fileName = new String(bytes);
    }
}
