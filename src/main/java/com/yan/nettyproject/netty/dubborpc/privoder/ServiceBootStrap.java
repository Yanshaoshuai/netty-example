package com.yan.nettyproject.netty.dubborpc.privoder;

import com.yan.nettyproject.netty.dubborpc.NettyServer;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /09 16:16
 * 启动服务提供者 netty server
 **/
public class ServiceBootStrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",8090);
    }
}
