package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.entity.UserFolder;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class List extends BaseService {
    private final Logger LOGGER = LogManager.getLogger();

    public List(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
    }

    @Override
    public boolean execute() {
        LOGGER.info("start print list");
        try {
            File file = new File(userFolder.getValue());
            System.out.println(userFolder.getValue());
            String[] strings = file.list();
            String contentList = Arrays.stream(strings).collect(Collectors.joining("\n"));
            ctx.channel().writeAndFlush(contentList);
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        LOGGER.info("list was printed");
        return true;
    }
}
