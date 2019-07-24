package com.admin.hanfusiadmin.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Slf4j
public class HttpRequestUtil {

    /**
     * get请求
     * @param url 请求地址
     * @return 返回结果
     */
    public static JSONObject sendGet(String url){
        String result = "";
        BufferedReader bufferedReader = null;
        JSONObject jsonObject = new JSONObject();
        try {
            URL requestUrl = new URL(url);

            // 打开和URL之间的连接
            URLConnection connection = requestUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            jsonObject = JSONObject.parseObject(result);
        } catch (Exception e){
            log.error("发送GET请求出现异常！异常信息：{}" + e);
            e.printStackTrace();
        } finally {
            closeBufferedReader(bufferedReader);
        }

        return jsonObject;
    }

    public static JSONObject sendPost(String url, Map<String, Object> map){
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        String result = "";
        JSONObject jsonObject = new JSONObject();
        try {
            URL requestUrl = new URL(url);

            // 打开和URL之间的连接
            URLConnection connection = requestUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 发送post请求必须设置如下两行
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(connection.getOutputStream());

            // 发送请求参数
            printWriter.print(getParam(map));

            // flush输出流的缓冲
            printWriter.flush();

            // 定义BufferedReader输入流来读取URL的响应
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            jsonObject = JSONObject.parseObject(result);
        } catch (Exception e){
            log.error("发送GET请求出现异常！异常信息：{}" + e);
            e.printStackTrace();
        } finally {
            closeBufferedReader(bufferedReader);
        }

        return jsonObject;
    }

    // 构造请求参数
    private static String getParam(Map<String, Object> map){
        StringBuffer stringBuffer = new StringBuffer();
        if (map != null && map.size() > 0){
            for (Map.Entry<String, Object> e: map.entrySet()){
                stringBuffer.append(e.getKey());
                stringBuffer.append("=");
                stringBuffer.append(e.getValue());
                stringBuffer.append("&");
            }
        }

        return stringBuffer.toString();
    }

    private static void closeBufferedReader(BufferedReader bufferedReader){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
        } catch (Exception e){
            log.error("关闭输入流出现异常！异常信息：{}" + e);
            e.printStackTrace();
        }
    }
}
