package com.yan.nettyproject.iomodel.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 18:32
 **/
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //获取SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("non-blocking --connecting do something else");
            }
        }

        //连接成功 发送数据
        String str = "I'm Mr.Yan";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // buffer.flip(); //position==0不需要这步
        socketChannel.write(buffer);
        System.in.read();
    }
}
