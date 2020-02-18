package sap.gb.cloud;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import sap.gb.cloud.handler.ServerHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private final int PORT = 8993;
    private String serverIp = "localhost";

    // при подключении через реализацию netty ничего не происходит...

//    public void run() throws Exception {
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(workerGroup)
//                    .channel(NioSocketChannel.class)
//                    .remoteAddress(new InetSocketAddress(serverIp, PORT))
//                    .option(ChannelOption.SO_KEEPALIVE, true)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        public void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new ServerHandler());
//                        }
//                    });
//            ChannelFuture chf = b.connect().sync();
//            chf.channel().closeFuture().sync();
//        } finally {
//            workerGroup.shutdownGracefully();
//        }
//    }

    public static void main(String[] args) throws Exception {
        //new Client().run();

        Socket socket = new Socket("localhost", 8993);
        DataInputStream incoming = new DataInputStream(socket.getInputStream());
        DataOutputStream outgoing = new DataOutputStream(socket.getOutputStream());


        File file = new File("/Users/mihailmihail/Desktop/gopro/100GOPRO/GOPR1114.JPG");
        File fileOut = new File("/Users/mihailmihail/Desktop/testJava/test.JPG");
        long fileSize = file.length();
        String fileName = file.getName();
        FileInputStream is = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(fileOut);

        //предположительно при написании клиента код ниже уедет в свой executor

        outgoing.writeInt("Push".length());
        outgoing.write("Push".getBytes());
        outgoing.writeInt(fileName.length());
        outgoing.write(fileName.getBytes());
        outgoing.writeLong(fileSize);
        int count = 0;
        byte[] bytes = new byte[1024];
        while (is.available() > 0) {
            is.read(bytes);
            outgoing.write(bytes);
        }

        //к этому коду добавиться парсинг заголовка и он также будет в своем executor


        int j = 0;
        byte[] bytess = new byte[1024];
        for (int i = 3946367; i > 0;  i -= j) {
            System.out.println(incoming.available());
            if (i < j)
                bytess = new byte[i];

            j = incoming.read(bytess);
            System.out.println(j);

            outputStream.write(bytess);

        }


        incoming.close();
        outgoing.close();

    }
}
