package com.neuqer.android.network.exception;

/**
 * 服务器定义错误
 *
 * @author chengkun
 * @since 2018/10/30 02:15
 */

public class ServerException extends RuntimeException{
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
