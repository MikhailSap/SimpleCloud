package sap.gb.cloud.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sap.gb.cloud.dao.UserDao;
import sap.gb.cloud.entity.User;
import sap.gb.cloud.entity.UserFolder;


public class Auth extends BaseService {
    private final Logger LOGGER = LogManager.getLogger();
    private int loginLength;
    private String login;
    private int passLength;
    private String pass;
    private byte[] bytes;

    public Auth(ChannelHandlerContext ctx, ByteBuf byteBuf, UserFolder userFolder) {
        super(ctx, byteBuf, userFolder);
        loginLength = byteBuf.readInt();
        bytes = new byte[loginLength];
        byteBuf.readBytes(bytes);
        login = new String(bytes);
        passLength = byteBuf.readInt();
        bytes = new byte[passLength];
        byteBuf.readBytes(bytes);
        pass = new String(bytes);
    }

    @Override
    public boolean execute() {
        LOGGER.info("start auth");
        User user = new UserDao().findByLogin(login);
        if (user == null) {
            ctx.channel().writeAndFlush("user not found");
            LOGGER.error("user not foud");
            return false;
        } else {
            if (user.getPass().equals(pass)) {
                userFolder.setValue(user.getStorage());
                ctx.channel().writeAndFlush("auth ok!");
                LOGGER.info("auth ok");
                return true;
            }
        }
        ctx.channel().writeAndFlush("auth failed, cause password is not correct");
        LOGGER.error("wrong password");
        return false;
    }
}
