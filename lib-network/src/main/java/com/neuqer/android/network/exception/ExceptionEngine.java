package com.neuqer.android.network.exception;


import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * 错误/异常处理工具
 *
 * @author chengkun
 * @since 2018/10/30 02:15
 */

public class ExceptionEngine {
    /**未知错误*/
    private static final int UN_KNOWN_ERROR = 1000;
    /**解析(服务器)数据错误*/
    private static final int PARSE_SERVER_DATA_ERROR = 1001;
    /**解析(客户端)数据错误*/
    public static final int PARSE_CLIENT_DATA_ERROR = 1002;
    /**网络连接错误*/
    private static final int CONNECT_ERROR = 1003;
    /**网络连接超时*/
    private static final int CONNECT_TIME_OUT_ERROR = 1004;

    public static ApiException handleException(Throwable throwable) {
        throwable.printStackTrace();
        ApiException apiException;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            apiException = new ApiException(throwable, httpException.code());
            apiException.setMsg("网络错误");
            return apiException;
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException
                || throwable instanceof MalformedJsonException) {
            apiException = new ApiException(throwable, PARSE_SERVER_DATA_ERROR);
            apiException.setMsg("解析数据错误");
            return apiException;
        } else if (throwable instanceof ConnectException) {
            apiException = new ApiException(throwable, CONNECT_ERROR);
            apiException.setMsg("网络连接失败");
            return apiException;
        } else if (throwable instanceof SocketTimeoutException) {
            apiException = new ApiException(throwable, CONNECT_TIME_OUT_ERROR);
            apiException.setMsg("网络连接超时");
            return apiException;
        } else if (throwable instanceof ServerException) {
            ServerException serverException = (ServerException) throwable;
            apiException = new ApiException(serverException, serverException.getCode());
            apiException.setMsg(serverException.getMsg());
            return apiException;
        } else {
            apiException = new ApiException(throwable, UN_KNOWN_ERROR);
            apiException.setMsg("未知错误");
            return apiException;
        }
    }
}
