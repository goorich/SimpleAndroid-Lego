package com.neuqer.android.runtime;

import android.app.Application;
import android.content.Context;

/**
 * App运行环境
 *
 * @author techflowing
 * @since 2018/9/15 下午3:44
 */
public class AppRuntime {

    /** Application */
    static Application sApplication;
    /** Debug标识 */
    static boolean sDebug;

    /**
     * 获取AppContext
     */
    public static Context getAppContext() {
        return sApplication;
    }

    /**
     * 获取Application
     */
    public static Application getApplication() {
        return sApplication;
    }

    /**
     * 全局Debug开关
     */
    public static boolean isDebug() {
        return sDebug;
    }
}
