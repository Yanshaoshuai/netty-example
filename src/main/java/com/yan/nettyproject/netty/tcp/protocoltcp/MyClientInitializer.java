package com.yan.nettyproject.netty.tcp.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 14:20
 **/
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //添加编码器
        pipeline.addLast(new MyMessageEncoder());
        //添加解码器
        pipeline.addLast(new MyMessageDecoder());
        //加入一个handler处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
