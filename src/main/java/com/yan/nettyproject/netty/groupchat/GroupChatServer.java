package com.yan.nettyproject.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /04 13:48
 **/
public class GroupChatServer {
    private int port;

    public GroupChatServer(int port) {
        this.port=port;
    }

    /**
     * 处理客户端请求
     */
    public void run() throws InterruptedException {
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast(new GroupChatServerHandler());
                            /**
                             * 添加心超时事件规则  -- 交给下一个handler处理超时事件(userEventTriggered方法处理)
                             * IdleStateHandler是netty提供的处理空闲状态的处理器
                             * readerIdleTime 多长时间没有读 发送一个心跳检测包检测是否连接
                             * writeIdleTime 多长时间没有写 发送一个心跳检测包检测是否连接
                             * allIdleTime 多长时间没有读写  发送一个心跳检测包检测是否连接
                             * 检测到连接断开就关闭通道
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            /**
                             * 添加处理心跳检测事件的处理器
                             */
                            pipeline.addLast(new HartBeatHandler());
                        }
                    });
            System.out.println("netty 服务器启动...");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(7000).run();
    }
}
