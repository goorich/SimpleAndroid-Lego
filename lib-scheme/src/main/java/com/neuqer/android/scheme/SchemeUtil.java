package com.neuqer.android.scheme;

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.Arrays;

/**
 * Scheme相关工具类
 *
 * @author techflowing
 * @since 2018/11/12 10:21 PM
 */
public class SchemeUtil {

    private static final String KEY_PARAMS = "params";

    /**
     * 构造Scheme对象
     *
     * @param scheme Scheme对象
     */
    public static SchemeEntity generateEntity(String scheme) {
        if (TextUtils.isEmpty(scheme)) {
            return null;
        }
        Uri uri = Uri.parse(scheme);
        if (uri == null) {
            return null;
        }
        String header = uri.getScheme();
        if (TextUtils.isEmpty(header)) {
            return null;
        }
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            return null;
        }
        String mainModule = null;
        String[] childModule = null;
        String[] modules = authority.split(".");
        if (modules.length == 0) {
            mainModule = authority;
        } else {
            mainModule = modules[0];
            if (modules.length > 1) {
                childModule = Arrays.copyOfRange(modules, 1, modules.length);
            }
        }
        String action = uri.getPath();
        if (action != null && action.startsWith(File.separator)) {
            action = action.substring(1, action.length());
        }
        if (TextUtils.isEmpty(action)) {
            return null;
        }
        String params = uri.getQueryParameter(KEY_PARAMS);
        return new SchemeEntity(header, mainModule, childModule, action, params);
    }
}
