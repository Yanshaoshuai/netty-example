package com.yan.nettyproject.iomodel.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 14:49
 **/
public class NIOFileChannelTransform {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream =
                new FileInputStream("E:\\File\\photo\\工作证明\\8a0e0ae3f639783ff2bf2d684d7a6ef.jpg");
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("a.jpg");
        FileChannel distChannel = fileOutputStream.getChannel();
        // sourceChannel --> distChannel
        distChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        fileInputStream.close();
        fileOutputStream.close();
    }
}
