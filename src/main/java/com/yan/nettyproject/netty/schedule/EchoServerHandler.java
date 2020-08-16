/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.yan.nettyproject.netty.schedule;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final EventExecutorGroup group=new DefaultEventExecutorGroup(16);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        System.out.println("EchoServerHandler#channelRead线程名称:"+Thread.currentThread().getName());
       /* ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("EchoServerHandler#execute#线程名称:"+Thread.currentThread().getName());
                ctx.writeAndFlush(Unpooled.copiedBuffer("Nice to meet you", CharsetUtil.UTF_8));
            }
        });*/
      /*  group.submit(new Callable<Object>() {//异步执行
            @Override
            public Object call() throws Exception {
                ByteBuf buf=(ByteBuf)msg;
                String body=buf.toString(CharsetUtil.UTF_8);
                TimeUnit.SECONDS.sleep(5);
                System.out.println("group#submit#call#线程名称:"+Thread.currentThread().getName());
                ctx.writeAndFlush(Unpooled.copiedBuffer("Nice to meet you", CharsetUtil.UTF_8));
                return null;
            }
        });*/
        ByteBuf buf=(ByteBuf)msg;
        String body=buf.toString(CharsetUtil.UTF_8);
        TimeUnit.SECONDS.sleep(5);
        System.out.println("普通执行方式#线程名称:"+Thread.currentThread().getName());
        ctx.writeAndFlush(Unpooled.copiedBuffer("Nice to meet you", CharsetUtil.UTF_8));
        System.out.println("go on");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
