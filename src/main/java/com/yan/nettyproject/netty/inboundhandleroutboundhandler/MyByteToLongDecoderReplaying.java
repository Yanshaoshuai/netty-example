package com.yan.nettyproject.netty.inboundhandleroutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 16:24
 **/
public class MyByteToLongDecoderReplaying extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoderReplaying#decode");
        //不用判断字节数
        out.add(in.readLong());
    }
}
