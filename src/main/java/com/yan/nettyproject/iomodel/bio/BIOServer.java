package com.yan.nettyproject.iomodel.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /30 21:57
 **/
public class BIOServer {
    public static void main(String[] args) throws IOException {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建服务端套接字
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端套接字创建成功...");
        while (true) {
            System.out.println(Thread.currentThread().getId() + "号线程" + "等待连接...");
            //监听客户端连接 阻塞1
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端...");
            //开启一个线程处理客户端连接
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //与客户端通信
                    handler(socket);
                }
            });
        }
    }

    /**
     * 处理套接字方法
     *
     * @param socket
     */
    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                //阻塞2
                System.out.println(Thread.currentThread().getId() + "号线程" + "等待客户端发送数据...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(Thread.currentThread().getId() + "号线程收到:" + new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
