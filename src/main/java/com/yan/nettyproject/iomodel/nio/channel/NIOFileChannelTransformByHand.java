package com.yan.nettyproject.iomodel.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 14:36
 **/
public class NIOFileChannelTransformByHand {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        //读channel
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        //写channel
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int length = 0;
        while ((length = inputStreamChannel.read(byteBuffer)) != -1) {//写
            //读
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
            //写
            byteBuffer.clear();
        }
        fileOutputStream.close();
        fileInputStream.close();
    }
}
