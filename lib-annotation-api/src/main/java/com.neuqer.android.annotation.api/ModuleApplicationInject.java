package com.neuqer.android.annotation.api;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ModuleApplication 注入调用
 *
 * @author techflowing
 * @since 2019-07-21 14:27
 */
public class ModuleApplicationInject {

    private static Object mInject;

    public static void onApplicationAttachBaseContext(Application application) {
        if (application == null) {
            return;
        }
        Object inject = getInjectObject(application);
        if (inject == null) {
            return;
        }
        try {
            Method method = inject.getClass().getMethod("onAttachBaseContext", Application.class);
            method.invoke(inject, application);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onApplicationCreate(Application application) {
        if (application == null) {
            return;
        }
        Object inject = getInjectObject(application);
        if (inject == null) {
            return;
        }
        try {
            Method method = inject.getClass().getMethod("onCreate", Application.class);
            method.invoke(inject, application);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getInjectObject(Context context) {
        if (mInject != null) {
            return mInject;
        }
        try {
            Class injectClass = Class.forName("com.neuqer.android.ModuleApplicationInject_inject");
            mInject = injectClass.newInstance();
            return mInject;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
