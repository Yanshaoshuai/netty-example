package com.yan.nettyproject.iomodel.nio.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 14:12
 **/
public class NIOFileChannelOutput {
    public static void main(String[] args) throws IOException {
        String str = "yanshaoshuai";
        FileOutputStream fileOutputStream = new FileOutputStream("out.txt");
        //通过流获取channel FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //把数据放入buffer
        byteBuffer.put(str.getBytes());
        //byteBuffer改为读模式
        byteBuffer.flip();
        //byteBuffer(读)-->fileChannel(写)
        int write = fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
