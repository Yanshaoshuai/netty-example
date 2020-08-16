package com.yan.nettyproject.iomodel.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /31 15:03
 **/
public class BufferPutGet {
    /**
     * 怎么put怎么get 否则会出现获取不正确或者抛出异常
     *
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(100);
        buffer.putLong(100000);
        buffer.putChar('闫');
        buffer.putShort((short) 1);

        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
