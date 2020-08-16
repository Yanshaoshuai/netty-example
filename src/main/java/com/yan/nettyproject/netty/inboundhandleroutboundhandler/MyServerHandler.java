package com.yan.nettyproject.netty.inboundhandleroutboundhandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 14:15
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("从客户端:"+channel.remoteAddress()+" 读取到了long->"+msg);
        ctx.writeAndFlush(98765L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
