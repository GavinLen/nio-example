package xyz.ieden.hello.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author gavin
 * @date 2018/8/26 22:58
 */
public class HelloServer {

    private static final Integer PORT_NUMBER = 7878;

    public static void main(String[] args) {
        // 处理接收进入的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();

                    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    // 解码
                    pipeline.addLast("decoder", new StringDecoder());
                    // 编码
                    pipeline.addLast("encoder", new StringEncoder());
                    // 处理逻辑
                    pipeline.addLast("handler", new HelloServerHandler());
                }
            });


            // 服务器绑定监听端口
            ChannelFuture future = bootstrap.bind(PORT_NUMBER).sync();
            // 等待服务器 socket 关闭
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
