package com.yan.nettyproject.netty.inboundhandleroutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 14:21
 **/
public class MyByteToLongEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyByteToLongEncoder#encode");
        System.out.println("msg="+msg);
        out.writeLong(msg);
    }
}
