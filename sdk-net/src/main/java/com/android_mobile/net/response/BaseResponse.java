package com.android_mobile.net.response;

import java.io.Serializable;
import java.net.HttpURLConnection;

/**
 * Created by mxh on 2016/11/22.
 * Describe：响应基类
 */

public class BaseResponse implements Serializable {

    /*响应信息*/
    private String message;
    /*响应码*/
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return statusCode;
    }

    public void setCode(int code) {
        this.statusCode = code;
    }

    public boolean isSuccess() {
        return statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED
                || statusCode == HttpURLConnection.HTTP_ACCEPTED || statusCode == HttpURLConnection.HTTP_NOT_AUTHORITATIVE;
    }
}
