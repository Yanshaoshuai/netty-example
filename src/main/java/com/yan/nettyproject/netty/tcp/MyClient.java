package com.yan.nettyproject.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 14:17
 **/
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap()
                                .group(group)
                                .channel(NioSocketChannel.class)
                                .handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8001).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
