package com.neuqer.android.runtime.module;

import android.app.Application;

import java.util.List;

/**
 * Module容器
 *
 * @author techflowing
 * @since 2019-09-17 23:10
 */
public class ModuleContainer implements IModuleApplication, ModuleListProvider {

    private static volatile ModuleContainer sInstance;
    private ModuleListProvider mModuleListProvider;

    private ModuleContainer() {
    }

    public static ModuleContainer getInstance() {
        if (sInstance == null) {
            synchronized (ModuleContainer.class) {
                sInstance = new ModuleContainer();
            }
        }
        return sInstance;
    }

    @Override
    public void onApplicationAttachBaseContext(Application application) {
        List<? extends AbsModuleApplication> list = getModuleList();
        if (list == null) {
            return;
        }
        for (AbsModuleApplication module : list) {
            module.onApplicationAttachBaseContext(application);
        }
    }

    @Override
    public void onApplicationCreate(Application application) {
        List<? extends AbsModuleApplication> list = getModuleList();
        if (list == null) {
            return;
        }
        for (AbsModuleApplication module : list) {
            module.onApplicationCreate(application);
        }
    }

    @Override
    public List<? extends AbsModuleApplication> getModuleList() {
        ModuleListProvider provider = getModuleListProvider();
        return provider != null ? provider.getModuleList() : null;
    }

    /**
     * 反射获取Gradle生成的类
     */
    private ModuleListProvider getModuleListProvider() {
        if (mModuleListProvider != null) {
            return mModuleListProvider;
        }
        try {
            Class injectClass = Class.forName(ModuleListProvider.CLASS_NAME);
            mModuleListProvider = (ModuleListProvider) injectClass.newInstance();
            return mModuleListProvider;
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
