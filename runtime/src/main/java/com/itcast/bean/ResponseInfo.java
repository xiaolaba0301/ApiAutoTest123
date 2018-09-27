package com.itcast.bean;

import org.apache.http.HttpEntity;

import java.util.Map;

public class ResponseInfo {
    private String responseBody;
    private Map<String,String> responseHeader;
    private String cookie;
    private int statusCode;
    private String httpEntity;

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Map<String, String> getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(Map<String, String> responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(String httpEntity) {
        this.httpEntity = httpEntity;
    }
}
