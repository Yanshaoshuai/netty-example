package com.yan.nettyproject.netty;

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
        for (Channel ch : NettyServer.getSocketChannels()) {
            ChannelHandlerContext channelHandlerContext = ch.pipeline().firstContext();
            ch.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("通过Channel引用添加任务" + channelHandlerContext.equals(ctx), CharsetUtil.UTF_8));
                }
            });
        }
        ctx.channel().eventLoop().execute(new Runnable() {//同步执行
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,I'm Server", CharsetUtil.UTF_8));
            }
        });
        ctx.channel().eventLoop().execute(new Runnable() {//同步执行
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("Nice to meet you", CharsetUtil.UTF_8));
            }
        });
        ctx.channel().eventLoop().schedule(new Runnable() {//同步执行
            @Override
            public void run() {
                ctx.writeAndFlush(Unpooled.copiedBuffer("schedule task", CharsetUtil.UTF_8));
            }
        }, 23, TimeUnit.SECONDS);

        System.out.println("当前线程:" + Thread.currentThread().getName());
        System.out.println("server ctx =" + ctx);
        //将msg转成ByteBuf(Netty提供的)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是:" + ctx.channel().remoteAddress());
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
