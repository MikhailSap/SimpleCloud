package sap.gb.cloud.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import sap.gb.cloud.Service.Service;
import java.io.FileInputStream;


public class ClientCommandHandler extends ChannelDuplexHandler {
    private enum State {RESOLVING_REQUEST, EXECUTING, RESPONSING}
    private State state = State.RESOLVING_REQUEST;
    private Service service;
    private String result;
    private final String servicesPath = "sap.gb.cloud.Service.";
    private FileInputStream fileInputStream;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connecting");
        fileInputStream = new FileInputStream("/Users/mihailmihail/Desktop/GOPR1114.JPG");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        if (state == State.RESOLVING_REQUEST) {
            int commandLength = byteBuf.readInt();
            byte[] bytes = new byte[commandLength];
            byteBuf.readBytes(bytes);
            StringBuilder command = new StringBuilder();
            for (byte b : bytes)
                if (b > 0) {
                    command.append((char) b);
                }
            System.out.println(command);
                // строчка отрабатывает что то навроде фабрики, возвращает экземпляр нужного сервиса
            service = (Service) Class.forName(servicesPath+command).getConstructor().newInstance();
            state = State.EXECUTING;
        }
        result = service.execute(byteBuf);
        byteBuf.release();

        if (!result.equals("inProcess")) {
            if (ctx.channel().isWritable()) {
                ctx.channel().writeAndFlush(result);
            }
        }

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //это переедет в свой сервис
        byte[] bytes = new byte[1024];
        ByteBuf byteBuf = ctx.alloc().directBuffer(3946367); // сдесь будет передаваться размер файла
        while (fileInputStream.available() > 0) {
            if (fileInputStream.available() < 1024)
                bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            byteBuf.writeBytes(bytes);
        }
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
