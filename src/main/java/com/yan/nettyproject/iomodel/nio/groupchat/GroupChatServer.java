package com.yan.nettyproject.iomodel.nio.groupchat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /01 14:00
 **/
public class GroupChatServer {//单Reactor单线程
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int port = 6667;

    //构造器
    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            //注册
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try {
            while (true) {
                int selectCount = selector.select();
                if (selectCount > 0) {
                    //处理事件
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {//无法发挥多线程性能
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            //有新连接可建立
                            SocketChannel clientChannel = serverSocketChannel.accept();
                            clientChannel.configureBlocking(false);
                            //注册客户端channel
                            clientChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(clientChannel.getRemoteAddress() + " 上线");
                        }
                        if (selectionKey.isReadable()) {
                            //可读
                            readData(selectionKey);
                        }
                        //删除selectionKey
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 读取客户端信息
     *
     * @param selectionKey
     */
    public void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        socketChannel = (SocketChannel) selectionKey.channel();
        //创建缓冲
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int readCount = 0;
            while ((readCount = socketChannel.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer.array(), 0, readCount);
                buffer.clear();
            }
            String msg = new String(byteArrayOutputStream.toByteArray());
            System.out.println("from client:" + socketChannel.getRemoteAddress()
                    + " msg:" + msg);
            //向其他客户端广播
            sendMsgToOthers(msg, socketChannel);
        } catch (IOException e) {
            try {
                System.err.println("client " + socketChannel.getRemoteAddress() + " 离线...");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发给其他客户端
     *
     * @param msg
     * @param self
     */
    private void sendMsgToOthers(String msg, SocketChannel self) {
        System.out.println("转发消息...");
        for (SelectionKey selectionKey : selector.keys()) {
            //取出channel
            SelectableChannel channel = selectionKey.channel();
            //只处理非self的client channel
            if (channel instanceof SocketChannel && !channel.equals(self)) {
                SocketChannel clientChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    clientChannel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
