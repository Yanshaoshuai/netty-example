package com.yan.nettyproject.iomodel.nio.buffer;

import java.nio.IntBuffer;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /30 22:43
 **/
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个可以存放5个int的buffer
        IntBuffer intBuffer = IntBuffer.allocate(6);
        for (int i = 0; i < 5; i++) {
            //向buffer放数据
            intBuffer.put(i * 2);
        }
        //切换为读模式
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
        //切换为写模式 并没有清除数据 属性修改为初始状态
        intBuffer.clear();
    }
}
