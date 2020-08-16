package com.yan.nettyproject.iomodel.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /01 16:12
 **/
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String fileName = "E:\\File\\download\\VisioPro2019Retail.img";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        //准备发送
        long startTimeMills = System.currentTimeMillis();
        //Linux下一个transformTo就可以完成传输
        //windows下一次transformTo只能传输8M,需要分段传输,而且需要控制传输时的位置
        //transferTo底层用了零拷贝技术
        long size = fileChannel.size();
        System.out.println("file size=" + size);
        long sum = 0;
        int eightM = 8 * 1024 * 1024;
        while (sum != size) {
            long transferToCount = 0;
            if (size < eightM) {
                transferToCount = fileChannel.transferTo(sum, size, socketChannel);
            } else {
                transferToCount = fileChannel.transferTo(sum, eightM, socketChannel);
            }
            sum += transferToCount;
        }
        System.out.println("发送的字节数=" + sum + " 耗时:" + (System.currentTimeMillis() - startTimeMills));
        fileChannel.close();

    }
}
