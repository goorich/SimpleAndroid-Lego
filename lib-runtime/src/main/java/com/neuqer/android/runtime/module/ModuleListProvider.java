package com.neuqer.android.runtime.module;

import java.util.List;

/**
 * ModuleApplication 列表提供
 *
 * @author techflowing
 * @since 2019-09-17 23:20
 */
public interface ModuleListProvider {

    /** Gradle 插件会生成此类，并实现{@link #getModuleList()}接口 */
    String CLASS_NAME = ModuleListProvider.class.getName() + "Impl";

    /**
     * 获取ModuleApplication 列表，此方法的真是实现由Gradle插件注入
     */
    List<? extends AbsModuleApplication> getModuleList();
}
