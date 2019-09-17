package com.neuqer.android.runtime;

import android.app.Application;
import android.content.Context;

import com.neuqer.android.runtime.module.ModuleContainer;

/**
 * 运行环境初始化类
 *
 * @author techflowing
 * @since 2018/9/15 下午3:44
 */
public class AppRuntimeInit {

    /**
     * {@link Application#attachBaseContext(Context)}
     */
    public static void onApplicationAttachBaseContext(Application application, boolean debug) {
        AppRuntime.sApplication = application;
        AppRuntime.sDebug = debug;
        ModuleContainer.getInstance().onApplicationAttachBaseContext(application);
    }

    /**
     * {@link Application#onCreate()}
     */
    public static void onApplicationCreate(Application application) {
        ModuleContainer.getInstance().onApplicationCreate(application);
    }
}
