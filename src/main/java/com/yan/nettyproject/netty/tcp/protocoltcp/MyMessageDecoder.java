package com.yan.nettyproject.netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 17:35
 **/
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder#decode");
        //解码
        int length = in.readInt();
        byte[] content=new byte[length];
        in.readBytes(content);
        MessageProtocol messageProtocol=new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        //交给下一个handler处理
        out.add(messageProtocol);
    }
}
