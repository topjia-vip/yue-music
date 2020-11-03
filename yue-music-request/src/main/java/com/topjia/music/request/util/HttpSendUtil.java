package com.topjia.music.request.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2020-05-24 15:11
 */
@Slf4j
public class HttpSendUtil {
    private static final int SUCCESS_CODE = 200;

    /**
     * 发送GET请求
     *
     * @param url 请求url
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendGet(String url, List<NameValuePair> nameValuePairList, RequestHeader header) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            // 创建Get请求
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.addParameters(nameValuePairList);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader("Accept", "*/*");
            httpGet.addHeader("Cache-Control", "no-cache");
            if (header != null) {
                if (!StringUtils.isEmpty(header.getReferer())) {
                    httpGet.addHeader("Referer", header.getReferer());
                }
                if (!StringUtils.isEmpty(header.getHost())) {
                    httpGet.addHeader("Host", header.getHost());
                }
                if (!StringUtils.isEmpty(header.getCookie())) {
                    httpGet.addHeader("Cookie", header.getCookie());
                }
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                try {
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                } catch (Exception e) {
                    return result;
                }
            } else {
                log.error("HttpClientService-line: {}, errorMsg{}", 97, "GET请求失败！");
            }
        } catch (Exception e) {
            log.error("HttpClientService-line: {}, Exception: {}", 100, e);
        } finally {
            response.close();
            httpClient.close();
        }
        return null;
    }


    public static Object sendGet(String url, RequestHeader header) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            // 创建Get请求
            URIBuilder uriBuilder = new URIBuilder(url);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader("Accept", "*/*");
            httpGet.addHeader("Cache-Control", "no-cache");
            if (header != null) {
                if (!StringUtils.isEmpty(header.getReferer())) {
                    httpGet.addHeader("Referer", header.getReferer());
                }
                if (!StringUtils.isEmpty(header.getHost())) {
                    httpGet.addHeader("Host", header.getHost());
                }
                if (!StringUtils.isEmpty(header.getCookie())) {
                    httpGet.addHeader("Cookie", header.getCookie());
                }
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                try {
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                } catch (Exception e) {
                    return result;
                }
            } else {
                log.error("HttpClientService-line: {}, errorMsg{}", 97, "GET请求失败！");
            }
        } catch (Exception e) {
            log.error("HttpClientService-line: {}, Exception: {}", 100, e);
        } finally {
            response.close();
            httpClient.close();
        }
        return null;
    }

    //发送post请求 携带json数据的
    public static Object sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception {
        //1.创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameters(nameValuePairList);
        //2.创建post请求方式实例
        HttpPost httpPost = new HttpPost(uriBuilder.build());

        //2.1设置请求头 发送的是json数据格式
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Cache-Control", "no-cache");

        //4.执行http的post请求
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        JSONObject jsonObject = null;
        try {
            response = httpClient.execute(httpPost);
            //5.对返回的数据进行处理
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                try {
                    jsonObject = JSONObject.parseObject(result);
                    return jsonObject;
                } catch (Exception e) {
                    return result;
                }
            } else {
                log.error("HttpClientService-line: {}, errorMsg{}", 97, "POST请求失败！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
            httpClient.close();
        }
        return null;
    }

    /**
     * 组织请求参数{参数名和参数值下标保持一致}
     *
     * @param params 参数名数组
     * @param values 参数值数组
     * @return 参数对象
     */
    public static List<NameValuePair> getParams(ArrayList<Object> params, ArrayList<Object> values) {
        /**
         * 校验参数合法性
         */
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            nameValuePairList.add(new BasicNameValuePair(params.get(i).toString(), values.get(i).toString()));
        }
        return nameValuePairList;
    }
}
