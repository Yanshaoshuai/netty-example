package com.yan.nettyproject.netty.codec2;

import com.yan.nettyproject.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /02 14:55
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪就会出发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发送Student或Worker对象
        int random=new Random().nextInt(3);
        MyDataInfo.MyMessage message=null;
        if(0==random){
            message=MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(
                            MyDataInfo.Student.newBuilder().setId(5).setName("海燕").build()
                    ).build();
        }else {
            message=MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(
                            MyDataInfo.Worker.newBuilder().setAge(20).setName("老王").build()
                    ).build();
        }
        ctx.writeAndFlush(message);
    }

    /**
     * 当通道可读事件时会触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址是:" + ctx.channel().remoteAddress());
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
