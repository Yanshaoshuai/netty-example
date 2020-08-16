package com.yan.nettyproject.netty.dubborpc;

import com.yan.nettyproject.netty.dubborpc.privoder.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /09 16:24
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg="+msg);
        //定义协议
        //比如以某字符串开头
        String msgStr = msg.toString();
        if(msgStr.startsWith("HelloService#hello#")){
            HelloServiceImpl helloService = new HelloServiceImpl();
            //截取信息
            String  realMsg=msgStr.substring(msgStr.lastIndexOf("#"))+1;
            helloService.hello(realMsg);
            ctx.writeAndFlush(realMsg);
        }
    }
}
