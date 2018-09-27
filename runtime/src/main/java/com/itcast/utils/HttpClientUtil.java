package com.itcast.utils;

import com.itcast.bean.ResponseInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    public static HttpGet httpGet;
    public static HttpPost httpPost;
    public static HttpClient httpClient;
    public static HttpResponse httpResponse;

    public static ResponseInfo get(String url) throws IOException {
        ResponseInfo responseInfo = new ResponseInfo();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(100)
                .setConnectTimeout(100)
                .setSocketTimeout(100)
                .build();
        httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(10)
                .setMaxConnPerRoute(10)
                .setDefaultRequestConfig(requestConfig)
                .build();
        httpGet = new HttpGet(url);
        httpResponse = httpClient.execute(httpGet);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            InputStream in = null;
            String body = "";
            HttpEntity httpEntity = httpResponse.getEntity();
            in = httpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                body += line;
            }
            responseInfo.setResponseBody(body);
        }
        try {
            httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseInfo;
    }

    public static ResponseInfo post1(String url, Map<String, Object> params) throws IOException {
        ResponseInfo responseInfo = new ResponseInfo();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(500)
                .setSocketTimeout(1000)
                .build();
        httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(10)
                .setMaxConnPerRoute(10)
                .setDefaultRequestConfig(requestConfig)
                .build();
        httpPost = new HttpPost(url);
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            list.add(new BasicNameValuePair(param.getKey().toString(), param.getValue().toString()));
        }
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
        httpPost.setEntity(urlEncodedFormEntity);
        httpResponse = httpClient.execute(httpPost);
        responseInfo.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        responseInfo.setHttpEntity(EntityUtils.toString(httpResponse.getEntity()));
        String body = "";
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream in = httpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = "";
            while ((line = br.readLine()) != null) {
                body += line;
            }
        }
        responseInfo.setResponseBody(body);
        return responseInfo;
    }


    public static String post(String url, String params) throws IOException {
        ResponseInfo responseInfo = new ResponseInfo();
        StringBuilder sb = new StringBuilder();
        httpClient = HttpClients.createDefault();

        httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(params, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        httpResponse = httpClient.execute(httpPost);
//        responseInfo.setStatusCode(httpResponse.getStatusLine().getStatusCode());
//        if(httpResponse.getStatusLine().getStatusCode() == 200){
        HttpEntity httpEntity = httpResponse.getEntity();
        String body = EntityUtils.toString(httpEntity);
        return body;
            /*InputStream in = httpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));*/

           /* String line = "";
            while ((line = br.readLine())!= null){
                sb.append(line);
            }
        }*/
        /*responseInfo.setResponseBody(body);
        return responseInfo;*/
    }
    public static String post(String url,Map<String,String> headers,Map<String,String> params,String stringEntity) throws IOException {
        String result = "";
        HttpClient httpClient;
        HttpResponse response;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(500)
                .setSocketTimeout(1000)
                .build();
        httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        if (headers != null && headers.size()>0){
            for (Map.Entry<String,String> entry:headers.entrySet()){
                post.addHeader(entry.getKey(),entry.getValue());
            }
        }
        if (params != null && params.size()>0){
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String,String> entry:params.entrySet()){
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
            post.setEntity(urlEncodedFormEntity);
        }
        if (stringEntity != null && stringEntity.length()>0){
            post.addHeader("Content-Type","text/xml");
            StringEntity stringEntity1 = new StringEntity(stringEntity,ContentType.APPLICATION_XML);
            post.setEntity(stringEntity1);
        }
        httpResponse = httpClient.execute(post);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("响应状态码："+statusCode);
        if (statusCode == 200){
            result = EntityUtils.toString(httpResponse.getEntity());
        }
        return result;
    }
    public static String post1(String url,String xml) throws IOException {
        HttpClient httpClient = null;
        String response = null;
        httpClient = new DefaultHttpClient();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(500)
                .setSocketTimeout(1000)
                .build();
        httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        StringEntity stringEntity = new StringEntity(xml,"UTF-8");
        httpPost.setEntity(stringEntity);
        HttpResponse response1 = httpClient.execute(httpPost);
        int statusCode = response1.getStatusLine().getStatusCode();
        if (statusCode == 200){
            HttpEntity httpEntity = response1.getEntity();
            response = EntityUtils.toString(httpEntity);
        }
        return response;
    }
    /*public static void main(String[] args) throws IOException {
        String json1 = "{'name':'xiaowang','age':'20'}";
        String body = post("http://localhost:8787/post/with/param",json1);
        System.out.print(body);
    }*/
}

