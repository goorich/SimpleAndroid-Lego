package com.neuqer.android.scheme;

/**
 * Scheme运行配置
 * 协议格式：协议头://主模块.子模块/action?params={"param1":"value1"}&其他参数
 *
 * @author techflowing
 * @since 2018/11/12 6:08 PM
 */
public class SchemeRuntime {

    /** 协议头 */
    private static String sSchemeHeader = "neuqer";

    public static String getSchemeHeader() {
        return sSchemeHeader;
    }

    public static void setSchemeHeader(String schemeHeader) {
        sSchemeHeader = schemeHeader;
    }
}
