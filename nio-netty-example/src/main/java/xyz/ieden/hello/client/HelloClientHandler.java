package xyz.ieden.hello.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gavin
 * @date 2018/8/26 23:28
 */
public class HelloClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        LOGGER.info("Address:{}, Msg:{}.", channelHandlerContext.channel().remoteAddress(), s);
    }

    /**
     * Channel 活跃
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Client Active.");
        super.channelActive(ctx);
    }

    /**
     * Channel 不活跃
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Client Inactive");
        super.channelInactive(ctx);
    }
}
