package com.yan.nettyproject.iomodel.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 15:07
 **/
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (int i = 0; i < 1024 / 4; i++) {
            buffer.putInt(i);
        }
        buffer.flip();
        //获取只读buffer
        ByteBuffer byteBuffer = buffer.asReadOnlyBuffer();
        System.out.println(byteBuffer.getClass());
        System.out.println(buffer.array());
        //尝试往只读buffer放入数据 会抛异常
        byteBuffer.clear();
        byteBuffer.putInt(1);
    }
}
