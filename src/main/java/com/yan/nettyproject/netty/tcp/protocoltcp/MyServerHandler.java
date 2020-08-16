package com.yan.nettyproject.netty.tcp.protocoltcp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 17:04
 **/
public class MyServerHandler  extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
       //接收数据并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息如下");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息次数="+(++this.count));

        //回复消息
        String responseContent=UUID.randomUUID().toString();
        int responseLength=responseContent.length();
        MessageProtocol messageProtocol=new MessageProtocol();
        messageProtocol.setLen(responseLength);
        messageProtocol.setContent(responseContent.getBytes());
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
