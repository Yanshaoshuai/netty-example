package com.yan.nettyproject.netty.dubborpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /09 16:37
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    /**
     * 上下文
     */
    private ChannelHandlerContext context;
    /**
     * 返回结果
     */
    private String result;
    /**
     * 客户端调用时传入的 参数
     */
    private String param;

    /**
     * 连接建立就调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接成功...");
        context=ctx;
    }

    /**
     * 收到服务器消息会调用
     * 对象锁 this
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result=msg.toString();
        //唤醒wait线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 被代理对象调用 发送数据给服务器 -->wait-->等待被唤醒-->返回结果
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object  call() throws Exception {
        context.writeAndFlush(param);
        wait();
        return result;
    }
    public void setParam(String param){
        this.param=param;
    }
}
