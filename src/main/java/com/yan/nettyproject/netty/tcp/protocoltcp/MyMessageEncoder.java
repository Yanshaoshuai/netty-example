package com.yan.nettyproject.netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 17:31
 **/
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncoder#encode");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
