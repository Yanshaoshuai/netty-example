package com.yan.nettyproject.netty.codec2;

import com.yan.nettyproject.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /02 14:33
 * 自定义一个handler 需要继承netty提供的某个HandlerAdapter
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        //根据dataType显示对应信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType==MyDataInfo.MyMessage.DataType.StudentType){
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("学生id:"+student.getId()+"name:"+student.getName());
        }else if(dataType==MyDataInfo.MyMessage.DataType.WorkerType){
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("工人name:"+worker.getName()+"age:"+worker.getAge());
        }else {
            System.out.println("传输的类型不正确");
        }
    }
}
