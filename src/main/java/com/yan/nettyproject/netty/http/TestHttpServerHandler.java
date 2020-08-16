package com.yan.nettyproject.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /03 15:02
 **/
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址:" + ctx.channel().remoteAddress());
            //回复信息给客户端
            ByteBuf buf = Unpooled.copiedBuffer("hello,I'm server", CharsetUtil.UTF_8);
            //构造一个HttpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
            //将response返回给http客户端
            ctx.writeAndFlush(response);
        }
    }
}
