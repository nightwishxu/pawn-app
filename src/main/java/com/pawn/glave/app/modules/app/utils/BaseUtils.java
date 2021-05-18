package com.pawn.glave.app.modules.app.utils;

import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xww
 * @Description:
 * @date 2021/5/1 14:24
 */
public class BaseUtils {


    public static MultipartFile downloadFile(String urlPath){
        		//把地址转换成URL对象
        try {
            URL url = new URL(urlPath);
            //创建http链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(60*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            MultipartFile file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static InputStream getImageInputStream(String urlPath){
        try {
            URL url = new URL(urlPath);
            //创建http链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(60*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


}
