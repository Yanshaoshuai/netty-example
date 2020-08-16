package com.yan.nettyproject.netty.dubborpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /09 16:50
 **/
public class NettyClient {
    //创建线程池
    private static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler clientHandler;
    private static void initClient() {
        clientHandler=new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(clientHandler);
                        }
                    });
            bootstrap.connect("127.0.0.1", 8090).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        /**
         * 获取代理对象
         */
        public Object getBean(Class<?> serviceClass, String providerName){
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{serviceClass}, (proxy, method, args) -> {//invoke 调用代理方法
                        if (clientHandler==null){
                            initClient();
                        }
                        //设置参数
                        clientHandler.setParam(providerName+args[0]);
                        return executorService.submit(clientHandler).get();
                    });
        }

}
