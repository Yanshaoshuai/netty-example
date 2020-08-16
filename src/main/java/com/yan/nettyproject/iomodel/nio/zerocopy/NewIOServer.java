package com.yan.nettyproject.iomodel.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /01 16:04
 **/
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(inetSocketAddress);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel clientChannel = serverSocketChannel.accept();
            int readCount = 0;
            while ((readCount = clientChannel.read(buffer)) != -1) {
                buffer.clear();
            }
        }
    }
}
