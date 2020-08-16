package com.yan.nettyproject.netty.http.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /04 13:48
 **/
public class LongConnectServer {
    private int port;

    public LongConnectServer(int port) {
        this.port=port;
    }

    /**
     * 处理客户端请求
     */
    public void run() throws InterruptedException {
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline加入编/解码器
                            pipeline.addLast("decoder",new HttpServerCodec());

                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * HttpObjectAggregator可以将Http分段数据聚合起来
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * websocket的数据是以帧(frame)的形式传递的
                             *
                             * 浏览器请求 ws://localhost:7000/xxx请求uri
                             *
                             * WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议,保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义Handler 处理业务逻辑
                            pipeline.addLast(new MyTestWebSocketFrameHandler());
                        }
                    });
            System.out.println("netty 服务器启动...");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        new LongConnectServer(7000).run();
    }
}
