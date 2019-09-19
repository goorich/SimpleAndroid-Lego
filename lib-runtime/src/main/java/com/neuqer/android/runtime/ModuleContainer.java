package com.neuqer.android.runtime;

import android.app.Application;

import java.util.List;

/**
 * Module容器
 *
 * @author techflowing
 * @since 2019-09-17 23:10
 */
public class ModuleContainer implements IModuleApplication, IModuleListProvider {

    private static volatile ModuleContainer sInstance;
    private IModuleListProvider mModuleListProvider;

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
        List<IModuleApplication> list = getModuleList();
        if (list == null) {
            return;
        }
        for (IModuleApplication module : list) {
            module.onApplicationAttachBaseContext(application);
        }
    }

    @Override
    public void onApplicationCreate(Application application) {
        List<IModuleApplication> list = getModuleList();
        if (list == null) {
            return;
        }
        for (IModuleApplication module : list) {
            module.onApplicationCreate(application);
        }
    }

    @Override
    public List<IModuleApplication> getModuleList() {
        IModuleListProvider provider = getModuleListProvider();
        return provider != null ? provider.getModuleList() : null;
    }

    /**
     * 反射获取Gradle生成的类
     */
    private IModuleListProvider getModuleListProvider() {
        if (mModuleListProvider != null) {
            return mModuleListProvider;
        }
        try {
            Class injectClass = Class.forName(IModuleListProvider.CLASS_NAME);
            mModuleListProvider = (IModuleListProvider) injectClass.newInstance();
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
