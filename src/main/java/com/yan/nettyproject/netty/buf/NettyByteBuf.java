package com.yan.nettyproject.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /04 11:29
 **/
public class NettyByteBuf {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        for(int i=0;i<buffer.capacity();i++){
            buffer.writeByte(i);
        }
        /**
         * netty提供的ByteBuf不需要用flip改成读模式
         * 底层维护了一个readIndex,writeIndex
         * 已读范围 0~readIndex
         * 可读范围 readIndex~writeIndex
         * 可写范围:writeIndex~capacity
         */
        for (int i=0;i<buffer.capacity();i++){
//            System.out.println(buffer.getByte(i));
            System.out.println(buffer.readByte());
        }
    }
}
