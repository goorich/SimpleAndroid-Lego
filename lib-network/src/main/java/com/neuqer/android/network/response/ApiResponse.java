package com.neuqer.android.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * 正确响应体
 *
 * @author FanHongyu.
 * @since 18/1/17 23:54.
 * email fanhongyu@hrsoft.net.
 */

public class ApiResponse<T> {

    /**
     * 状态码
     */
    @SerializedName("code")
    private int code;

    /**
     * 返回信息
     */
    @SerializedName("message")
    private String msg;

    /**
     * 返回具体数据
     */
    @SerializedName("data")
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
