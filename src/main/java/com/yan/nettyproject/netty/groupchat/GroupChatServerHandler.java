package com.yan.nettyproject.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /04 13:57
 **/
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * GlobalEventExecutor.INSTANCE --全局事件执行器 是一个单例
     * channel组 管理所有的channel
     */
   private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 一旦建立连接 第一个被执行
     * 将当前channel加入到 channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //该方法会遍历channelGroup中的所有channel,并发送消息
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天");
        channelGroup.add(channel);
    }

    /**
     * channel 变为活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了~");
    }

    /**
     * channel 变为非活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了~");
    }

    /**
     * 断开连接触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"离开了~");
        System.out.println("channelGroup size"+channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //遍历channelGroup 发送消息,排除自己
        channelGroup.forEach(ch -> {
            if(ch!=channel){
                ch.writeAndFlush("[客户]"+channel.remoteAddress()+"发送消息:"+msg);
            }
            //自己不发送
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
