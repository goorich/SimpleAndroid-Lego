package com.neuqer.android.scheme;

/**
 * Scheme实体对象信息
 *
 * @author techflowing
 * @since 2018/11/12 9:59 PM
 */
public class SchemeEntity {

    /** 协议头 */
    private String mHeader;
    /** 主模块名称 */
    private String mMainModule;
    /** 子模块名称 */
    private String[] mChildModule;
    /** Action名称 */
    private String mAction;
    /** 参数值 */
    private String mParams;

    public SchemeEntity(String header, String mainModule, String action) {
        mHeader = header;
        mMainModule = mainModule;
        mAction = action;
    }

    public SchemeEntity(String header, String mainModule, String[] childModule, String action, String params) {
        mHeader = header;
        mMainModule = mainModule;
        mChildModule = childModule;
        mAction = action;
        mParams = params;
    }

    public void setChildModule(String[] childModule) {
        mChildModule = childModule;
    }

    public void setParams(String params) {
        mParams = params;
    }

    public String getHeader() {
        return mHeader;
    }

    public String getMainModule() {
        return mMainModule;
    }

    public String[] getChildModule() {
        return mChildModule;
    }

    public String getAction() {
        return mAction;
    }

    public String getParams() {
        return mParams;
    }
}


