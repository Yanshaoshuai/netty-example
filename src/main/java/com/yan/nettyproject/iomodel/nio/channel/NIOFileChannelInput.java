package com.yan.nettyproject.iomodel.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 14:26
 **/
public class NIOFileChannelInput {
    public static void main(String[] args) throws IOException {
        File file = new File("out.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取channel
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //fileChannel(读)-->byteBuffer(写)
        int read = fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
