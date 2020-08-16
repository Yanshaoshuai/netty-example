package com.yan.nettyproject.iomodel.nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 16:47
 **/
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(3);
        byteBuffers[0] = byteBuffer1;
        byteBuffers[1] = byteBuffer2;
        //等待客户端连接
        SocketChannel clientChannel = serverSocketChannel.accept();
        int limit = 8;
        while (true) {
            long lenRead = 0;
            while (lenRead < limit) {
                long read = clientChannel.read(byteBuffers);
                lenRead += read;
                System.out.println("has read lenRead=" + lenRead);
                //打印 buffer状态
                Arrays.asList(byteBuffers).stream().map(
                        buffer -> "position:" + buffer.position() + " limit:" + buffer.limit()
                ).forEach(System.out::println);

            }
            //改为读模式
            Arrays.stream(byteBuffers).forEach(buffer -> buffer.flip());
            //回写到客户端
            long lenWrite = 0;
            while (lenWrite < limit) {
                long write = clientChannel.write(byteBuffers);
                lenWrite += write;
                System.out.println("has write lenWrite=" + lenRead);
                //打印 buffer状态
                Arrays.asList(byteBuffers).stream().map(
                        buffer -> "position:" + buffer.position() + " limit:" + buffer.limit()
                ).forEach(System.out::println);
            }
            //改为写模式
            Arrays.stream(byteBuffers).forEach(buffer -> buffer.clear());
        }
    }
}
