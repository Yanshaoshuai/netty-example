package com.yan.nettyproject.netty.dubborpc.privoder;

import com.yan.nettyproject.netty.dubborpc.publicinterface.HelloService;
import io.netty.util.internal.StringUtil;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /09 16:13
 **/
public class HelloServiceImpl implements HelloService {
    /**
     *
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息="+msg);
        if(!StringUtil.isNullOrEmpty(msg)){
            return "你好客户端,我已经收到你的消息["+msg+"]";
        }
        return "你好客户端,我已经收到你的消息";
    }
}
