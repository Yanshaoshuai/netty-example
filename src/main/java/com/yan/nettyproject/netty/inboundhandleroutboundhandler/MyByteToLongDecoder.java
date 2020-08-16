package com.yan.nettyproject.netty.inboundhandleroutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /05 14:12
 **/
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param ctx 上下文
     * @param in 入站的ByteBuf
     * @param out List集合 将解码的数据交给下一个handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()>=8){
            System.out.println("MyByteToLongDecoder#decode");
            out.add(in.readLong());
        }
    }
}
