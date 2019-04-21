package com.neuqer.android.scheme;

/**
 * Scheme分发器基类，所有分发器需要实现此类
 *
 * @author techflowing
 * @since 2018/11/12 9:50 PM
 */
public abstract class AbsSchemeDispatch implements ISchemeDispatch {

    /**
     * 获取子模块的分发器，可按需重写
     *
     * @param name 子模块名称
     * @param <T>  分发器Class对象
     */
    protected <T extends AbsSchemeDispatch> T getChildDispatch(String name) {
        return null;
    }
}
