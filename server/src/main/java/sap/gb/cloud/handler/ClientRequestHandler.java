package sap.gb.cloud.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;
import sap.gb.cloud.service.Service;


public class ClientRequestHandler extends ChannelDuplexHandler {
    private final Logger LOGGER = LogManager.getLogger();
    private enum State {AUTH, RESOLVING_REQUEST, EXECUTING}
    private State state;
    private final String AUTH_SERVICE_NAME;
    private Service service;
    private UserFolder userFolder;
    private boolean isComplete;
    private final String SERVICE_PATH;

    public ClientRequestHandler() {
        AUTH_SERVICE_NAME = "Auth";
        SERVICE_PATH = "sap.gb.cloud.service.";
        userFolder = new UserFolder();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client connecting");
        LOGGER.info("client connecting");
        state = State.AUTH;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf) msg;
        String serviceName;
        if (state == State.AUTH) {
            LOGGER.info("state is auth");
            serviceName = AUTH_SERVICE_NAME;
            service = getService(serviceName, ctx, message, userFolder);
        }
        if (state == State.RESOLVING_REQUEST) {
            LOGGER.info("state is resolving request");
            int commandLength = message.readInt();
            byte[] bytes = new byte[commandLength];
            message.readBytes(bytes);
            serviceName = new String(bytes);
            service = getService(serviceName, ctx, message, userFolder);
            state = State.EXECUTING;
        }
        isComplete = service.execute();
        message.release();

        if (isComplete) {
            System.out.println(isComplete);
            state = State.RESOLVING_REQUEST;
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LOGGER.info("response to client");
        String messageToClient = (String) msg;
        int messageLength = messageToClient.length();
        byte[] bytes = messageToClient.getBytes();
        ByteBuf byteBuf = ctx.alloc().directBuffer(messageLength);
        byteBuf.writeInt(messageLength);
        byteBuf.writeBytes(bytes);
        ChannelFuture f = ctx.writeAndFlush(byteBuf);
        f.addListener((future) -> System.out.println("it is done"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        LOGGER.error(cause);
    }

    private Service getService(String serviceName, ChannelHandlerContext ctx, ByteBuf message, UserFolder userFolder) throws Exception {
        return (Service) Class
                .forName(SERVICE_PATH+serviceName)
                .getConstructor(ChannelHandlerContext.class, ByteBuf.class, UserFolder.class)
                .newInstance(ctx, message, userFolder);
    }
}
