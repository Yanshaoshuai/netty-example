package com.yan.nettyproject.iomodel.nio.groupchat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /01 14:38
 **/
public class GroupChatClient {
    private static final String host = "127.0.0.1";
    private static final int port = 6667;
    private Selector selector;
    private SocketChannel clientChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            //连接服务器
            clientChannel = SocketChannel.open(new InetSocketAddress(host, port));
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            username = clientChannel.getLocalAddress().toString().substring(1);
            System.out.println("client " + username + " is ok!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        msg = username + " 说: " + msg;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            clientChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg() {
        try {
            int prepareChannelCount = selector.select();
            if (prepareChannelCount > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int readCount = 0;
                        while ((readCount = channel.read(buffer)) > 0) {
                            byteArrayOutputStream.write(buffer.array(), 0, readCount);
                            buffer.clear();
                        }
                        String msg = new String(byteArrayOutputStream.toByteArray());
                        System.out.println("client read msg:" + msg);
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient client = new GroupChatClient();
        Runnable target = () -> {
            while (true) {
                client.readMsg();
            }
        };
        new Thread(target).start();
        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            client.sendMsg(msg);
        }
    }
}
