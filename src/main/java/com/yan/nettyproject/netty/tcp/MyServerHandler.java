package com.yan.nettyproject.netty.tcp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Random;
import java.util.UUID;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 17:04
 **/
public class MyServerHandler  extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer=new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器端接收到数据:"+message);
        System.out.println("服务器接收到消息量:"+(++this.count));
        //回写数据
        ByteBuf buf = Unpooled.copiedBuffer(UUID.randomUUID().toString()+"___", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
