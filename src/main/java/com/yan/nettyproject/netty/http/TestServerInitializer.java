package com.yan.nettyproject.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /03 14:58
 **/
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //得到pipeline
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty提供的httpServerCodec codec=>[coder - decoder]
        //HttpServerCodec 是netty提供的处理http的编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //增加一个Handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
