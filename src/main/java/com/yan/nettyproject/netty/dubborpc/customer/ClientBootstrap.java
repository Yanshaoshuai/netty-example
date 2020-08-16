package com.yan.nettyproject.netty.dubborpc.customer;

import com.yan.nettyproject.netty.dubborpc.NettyClient;
import com.yan.nettyproject.netty.dubborpc.publicinterface.HelloService;

import java.util.concurrent.TimeUnit;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /09 21:23
 **/
public class ClientBootstrap {
    /**
     * 这里定义协议头
     */
    public static final String providerName="HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        //创建消费者
        NettyClient customer=new NettyClient();
        HelloService helloService = (HelloService) customer.getBean(HelloService.class, providerName);
        //通过代理对象调用服务提供者的方法(服务)
        String result = helloService.hello("你好 dubbo");
        System.out.println("调用的结果result="+result);
        TimeUnit.SECONDS.sleep(2);
    }
}
