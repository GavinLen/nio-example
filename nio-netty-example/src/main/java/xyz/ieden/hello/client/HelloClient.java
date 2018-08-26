package xyz.ieden.hello.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author gavin
 * @date 2018/8/26 23:18
 */
public class HelloClient {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 7878;

    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        /**
                         * 与 server 保持一致
                         */

                        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        // 解码
                        pipeline.addLast("decoder", new StringDecoder());
                        // 编码
                        pipeline.addLast("encoder", new StringEncoder());
                        // 处理逻辑
                        pipeline.addLast("handler", new HelloClientHandler());
                    }
                });

        try {
            // 连接服务器
            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
            // 控制台输入
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    continue;
                }

                channel.writeAndFlush(line + "\r\n");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }

    }

}
