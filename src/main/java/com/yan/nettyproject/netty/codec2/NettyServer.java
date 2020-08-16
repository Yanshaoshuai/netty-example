package com.yan.nettyproject.netty.codec2;

import com.yan.nettyproject.netty.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /02 14:19
 **/
public class NettyServer {



    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建bossGroup workerGroup
         * 默认创建 2*cpu最大线程数 个NioEventLoop来处理事件 8核心16线程--> nThreads=32
         * 每个NioEventLoop都有一个Selector 是一个不断轮询执行处理任务的线程
         */

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务器端的启动对象,配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列 连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象
                        //给pipeline设置处理器

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //指定对那个类型解码
                            pipeline.addLast("decoder",new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器准备就绪...");
            //绑定一个端口 同步处理 生成一个ChannelFuture对象
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            //注册监听事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("绑定6668端口成功...");
                    } else {
                        System.out.println("绑定6668端口失败...");
                    }
                }
            });
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅的关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
