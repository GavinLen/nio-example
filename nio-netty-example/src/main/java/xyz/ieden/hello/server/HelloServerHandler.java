package xyz.ieden.hello.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * HelloServer 处理
 *
 * @author gavin
 * @date 2018/8/26 23:07
 */
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServerHandler.class);

    /**
     * Chanel 读取
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        LOGGER.info("Address:{}, Msg:{}.", channelHandlerContext.channel().remoteAddress(), s);
        channelHandlerContext.writeAndFlush("Received your message !\n");
    }

    /**
     * Channel 活跃
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("RemoteAddress [{}] Active.", ctx.channel().remoteAddress());
        ctx.writeAndFlush(String.format("Welcome to %s Server!\n", InetAddress.getLocalHost().getHostName()));
        super.channelActive(ctx);
    }
}
