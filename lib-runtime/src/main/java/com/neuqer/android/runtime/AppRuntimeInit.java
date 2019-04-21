package com.neuqer.android.runtime;

import android.app.Application;

/**
 * 运行环境初始化类
 *
 * @author techflowing
 * @since 2018/9/15 下午3:44
 */
public class AppRuntimeInit {

    /**
     * 设置运行环境
     */
    public static void onApplicationAttachBaseContext(Application application, boolean debug) {
        AppRuntime.sApplication = application;
        AppRuntime.sDebug = debug;
    }
}
