package com.yan.nettyproject.netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 17:00
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据
        for (int i = 0; i < 10; i++) {
            String message="今天天气冷,我们吃火锅";
            byte[] content = message.getBytes(CharsetUtil.UTF_8);
            int length=message.getBytes().length;
            //创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        String message=new String(content,CharsetUtil.UTF_8);
        System.out.println("客户端接收到消息,内容如下");
        System.out.println("长度="+len);
        System.out.println("内容="+message);
        System.out.println("客户端接收到消息数量:"+(++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
