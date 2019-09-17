package com.neuqer.android.runtime.module;

import android.app.Application;
import android.content.Context;

import com.neuqer.android.runtime.AppRuntime;

/**
 * Module Application
 *
 * @author techflowing
 * @since 2019-09-17 22:57
 */
public abstract class AbsModuleApplication implements IModuleApplication {

    /** DEBUG标识 */
    public static final boolean DEBUG = AppRuntime.isDebug();

    /**
     * 获取AppContext
     */
    public static Context getAppContext() {
        return AppRuntime.getAppContext();
    }

    /**
     * 获取Application
     */
    public static Application getApplication() {
        return AppRuntime.getApplication();
    }
}
