package com.zp.myhomemanager.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetUtil {

    /**
     *
     * @param aUrl 网址
     * @param aEncode 编码
     * @return 返回的HTML代码
     * @throws Exception 对外抛出异常
     */
    public static String getHTML(String aUrl, String aEncode) throws Exception {
        URL url = new URL(aUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len = inputStream.read(buffer)) != -1){
                outStream.write(buffer, 0, len);
            }
            String htmlStr = new String(outStream.toByteArray(), aEncode);
            inputStream.close();
            outStream.close();
            return htmlStr;
        }
        else if (conn.getResponseCode() == 301 || conn.getResponseCode() == 302) {
            //得到重定向的地址
            String location = conn.getHeaderField("Location");
            URL u1 = new URL(location);
            HttpsURLConnection uc1 = (HttpsURLConnection) u1.openConnection();
            uc1.setRequestMethod("GET");
            uc1.setConnectTimeout(5000);
            uc1.setReadTimeout(5000);
            if (uc1.getResponseCode() == 200) {
                InputStream inputStream = uc1.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while( (len = inputStream.read(buffer)) != -1){
                    outStream.write(buffer, 0, len);
                }
                String htmlStr = new String(outStream.toByteArray(), aEncode);
                inputStream.close();
                outStream.close();
                return htmlStr;
            }
        }

        return null;
    }

    public static String getHtml(String path) throws Exception {
        // 通过网络地址创建URL对象
        URL url = new URL(path);
        // 根据URL
        // 打开连接，URL.openConnection函数会根据URL的类型，返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设定URL的请求类别，有POST、GET 两类
        conn.setRequestMethod("GET");
        //设置从主机读取数据超时（单位：毫秒）
        conn.setConnectTimeout(5000);
        //设置连接主机超时（单位：毫秒）
        conn.setReadTimeout(5000);
        // 通过打开的连接读取的输入流,获取html数据
        InputStream inStream = conn.getInputStream();
        // 得到html的二进制数据
        byte[] data = readInputStream(inStream);
        // 是用指定的字符集解码指定的字节数组构造一个新的字符串
        String html = new String(data, "utf-8");
        return html;
    }

    /**
     * 读取输入流，得到html的二进制数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}

