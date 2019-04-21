package com.neuqer.android.scheme;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Scheme集合工厂
 *
 * @author techflowing
 * @since 2018/11/12 9:47 PM
 */
public class SchemeFactory {

    /** 模块名称：分发器映射集合 */
    private static HashMap<String, Class<? extends AbsSchemeDispatch>> sSchemeDispatchMap;

    static {
        sSchemeDispatchMap = new HashMap<>();
    }

    /**
     * 注册Scheme
     *
     * @param name 模块名称
     * @param cls  类名
     */
    public static void registerDispatch(String name, Class<? extends AbsSchemeDispatch> cls) {
        sSchemeDispatchMap.put(name, cls);
    }

    /**
     * 获取分发器类
     *
     * @param name 模块名称
     * @return 类名
     */
    public static Class<? extends AbsSchemeDispatch> getDispatch(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return sSchemeDispatchMap.get(name);
    }
}
