package com.neuqer.android.scheme;

import android.content.Context;

/**
 * Scheme分发器抽象接口
 *
 * @author techflowing
 * @since 2018/11/12 10:18 PM
 */
public interface ISchemeDispatch {
    /**
     * 分发处理Action
     *
     * @param context  上下文
     * @param entity   Scheme信息
     * @param callback 回调
     * @return 是否成功处理
     */
    boolean dispatch(Context context, SchemeEntity entity, SchemeCallback callback);
}
