package com.neuqer.android.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json相关工具类
 *
 * @author techflowing
 * @since 2018/10/31 11:59 PM
 */
public class JsonUtil {

    /**
     * 解析String 为JSONObject
     *
     * @param content 内容
     */
    public static JSONObject parseString(String content) {
        if (TextUtils.isEmpty(content)) {
            return new JSONObject();
        }
        try {
            return new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
