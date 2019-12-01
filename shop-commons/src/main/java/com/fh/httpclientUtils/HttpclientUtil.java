package com.fh.httpclientUtils;

import com.alibaba.fastjson.JSON;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpclientUtil {
    //不变的是 创建客户端
    public  static CloseableHttpClient httpClient;
    static{
         // 配置连接超时信息
         RequestConfig requestConfig = RequestConfig.custom()
                 // 设置与服务器连接的超时时间(单位毫秒)
                 .setConnectionRequestTimeout(5000)
                 // 设置访问接口的超时时间
                 .setSocketTimeout(15000).build();
         //创建http请求客户端
        // 将配置信息 运用到这个客户端里
          httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
     }

     public  static  String doGet(String url) {
         HttpGet httpGet = new HttpGet(url);
         // 创建响应模型
         CloseableHttpResponse response = null;
         try {
             response = httpClient.execute(httpGet);
             HttpEntity entity = response.getEntity();
             return EntityUtils.toString(entity, "UTF-8");
         } catch (HttpHostConnectException e) {
             e.printStackTrace();
             return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_CONNECT_ERROR));
         } catch (SocketTimeoutException e) {
             e.printStackTrace();
             return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_BUSYNESS));
         } catch (IOException e) {
             e.printStackTrace();
             return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_NO_EXCEPTION));
         }
     }

     public  static  String  doDelete(String url){
         HttpDelete  httpDelete = new HttpDelete(url);
         //创建响应模型
         CloseableHttpResponse response = null;
         try {
             response = httpClient.execute(httpDelete);
             HttpEntity entity = response.getEntity();
             return EntityUtils.toString(entity,"UTF-8");
         }catch (HttpHostConnectException e){
             e.printStackTrace();
             return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_CONNECT_ERROR));
         } catch (SocketTimeoutException e){
             e.printStackTrace();
             return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_BUSYNESS));
         }
         catch (IOException e) {
             e.printStackTrace();
             return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_NO_EXCEPTION));
         }
     }

    public static String doPut(String url, Map<String, String> parameterMap) {
        //声明http请求的方式
        HttpPut httpPut = new HttpPut(url);
        //处理参数
        if (parameterMap != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            parameterMap.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                list.add(new BasicNameValuePair(key, value));
            });
            try {
                //处理参数
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
                //将参数加入到http请求中
                httpPut.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            //服务器连接超时异常
            return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_CONNECT_ERROR));
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_BUSYNESS));
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_NO_EXCEPTION));
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String doPost(String url, Map<String, String> parameterMap) {
        //声明http请求的方式
        HttpPost httpPost = new HttpPost(url);
        //处理参数
        if (parameterMap != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            parameterMap.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                list.add(new BasicNameValuePair(key, value));
            });
            try {
                //处理参数
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
                //将参数加入到http请求中
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            //服务器连接超时异常
            return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_CONNECT_ERROR));
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_BUSYNESS));
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseServer.error(ServerEnum.SERVER_NO_EXCEPTION));
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
