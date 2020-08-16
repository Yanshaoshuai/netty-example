package com.yan.nettyproject.iomodel.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 15:14
 **/
public class MappedBufferTest {
    /**
     * Mapped buffer 可以直接在内存(堆外内存)中修改 操作系统不需要再拷贝一次
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * mode 映射模式
         * position 映射开始位置
         * size     映射的尺寸
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'Y');
        mappedByteBuffer.put(3, (byte) 'S');
        randomAccessFile.close();
    }
}
