package sap.gb.cloud.Service;

import io.netty.buffer.ByteBuf;

public interface Service {
    String execute(ByteBuf byteBuf);
}
