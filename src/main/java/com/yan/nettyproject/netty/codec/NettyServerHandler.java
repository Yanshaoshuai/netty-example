package com.yan.nettyproject.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /02 14:33
 * 自定义一个handler 需要继承netty提供的某个HandlerAdapter
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据事件
     *
     * @param ctx 上下文对象 含有 pipeline channel地址...
     * @param msg 客户端发送的数据 默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取从客户端发送的StudentPOJO.Student
        StudentPOJO.Student student=(StudentPOJO.Student)msg;
        System.out.println("客户都发送的数据:student.id="+student.getId()+" student.name="+student.getName());
    }

    /**
     * 读取事件完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存 并刷新
        //一般会对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~", CharsetUtil.UTF_8));

    }

    /**
     * 处理异常 一般要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
