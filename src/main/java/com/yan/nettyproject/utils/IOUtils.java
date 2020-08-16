package com.yan.nettyproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Mr.Yan
 * @Date 2020 / 07 /30 23:11
 **/
public class IOUtils {
    /**
     * 完整读完inputStream 再把返回字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        inputStream.close();
        bos.close();
        return bos.toByteArray();
    }
}
