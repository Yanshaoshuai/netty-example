package com.yan.nettyproject.iomodel.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 18:01
 **/
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建Selector
        Selector selector = Selector.open();
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口 并开始监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //注册到selector 监听事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环监听
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待了一秒....没有监听到事件");
                continue;
            }
            //获取有事件发生的Channel对应的selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                //获取SelectionKey
                SelectionKey selectionKey = iterator.next();
                //判断事件类型
                if (selectionKey.isAcceptable()) {//连接事件
                    SocketChannel clientChannel = serverSocketChannel.accept();
                    System.out.println("成功建立了一个客户端连接:hashcode==" + clientChannel.hashCode());
                    //设置成非阻塞
                    clientChannel.configureBlocking(false);
                    //将clientChannel注册到Selector 监听可读事件
                    //并关联一个buffer
                    clientChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (selectionKey.isReadable()) {//可读事件
                    //获取channel
                    SelectableChannel channel = selectionKey.channel();
                    SocketChannel socketChannel = null;
                    if (channel instanceof SocketChannel) {
                        socketChannel = (SocketChannel) channel;
                    }
                    if (socketChannel == null) {
                        continue;
                    }
                    //获取到该channel关联的buffer
                    Object attachment = selectionKey.attachment();
                    ByteBuffer byteBuffer = null;
                    if (attachment instanceof ByteBuffer) {
                        byteBuffer = (ByteBuffer) attachment;
                    }
                    if (byteBuffer == null) {
                        continue;
                    }
                    //读入buffer
                    int read = socketChannel.read(byteBuffer);
                    System.out.println("收到客户端发来的数据:" + new String(byteBuffer.array()));
                }
                //手动删除处理过的SelectionKey
                iterator.remove();
            }
        }
    }
}
