package com.neuqer.android.runtime;

import java.util.List;

/**
 * ModuleApplication 列表提供
 * <p>
 * 此类不要随意修改，和Gradle 插件 Gather 有关
 *
 * @author techflowing
 * @since 2019-09-17 23:20
 */
public interface IModuleListProvider {

    /** Gradle 插件会生成此类，并实现{@link #getModuleList()}接口 */
    String CLASS_NAME = "com.neuqer.android.runtime.ModuleListProviderImpl";

    /**
     * 获取 ModuleApplication 列表，此方法的真是实现由Gradle插件注入
     */
    List<IModuleApplication> getModuleList();
}
