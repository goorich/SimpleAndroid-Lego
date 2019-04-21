package com.neuqer.android.scheme;

import android.content.Context;
import android.text.TextUtils;

/**
 * Scheme分发器入口
 *
 * @author techflowing
 * @since 2018/11/12 10:16 PM
 */
public class MainSchemeDispatch extends AbsSchemeDispatch {

    /** 上下文 */
    private Context mContext;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public MainSchemeDispatch(Context context) {
        mContext = context;
    }

    /**
     * 分发Scheme
     *
     * @param scheme scheme协议
     * @return 是否成功
     */
    public boolean dispatch(String scheme) {
        return dispatch(scheme, null);
    }

    /**
     * 分发Scheme
     *
     * @param scheme   scheme协议
     * @param callback 回调
     * @return 是否成功
     */
    public boolean dispatch(String scheme, SchemeCallback callback) {
        if (TextUtils.isEmpty(scheme)) {
            return false;
        }
        SchemeEntity entity = SchemeUtil.generateEntity(scheme);
        if (entity == null) {
            return false;
        }
        return dispatch(mContext, entity, callback);
    }

    @Override
    public boolean dispatch(Context context, SchemeEntity entity, SchemeCallback callback) {
        if (!TextUtils.equals(SchemeRuntime.getSchemeHeader(), entity.getHeader())) {
            return false;
        }
        try {
            Class<? extends AbsSchemeDispatch> dispatchCls = SchemeFactory.getDispatch(entity.getMainModule());
            if (dispatchCls != null) {
                AbsSchemeDispatch dispatch = dispatchCls.newInstance();
                if (dispatch != null) {
                    if (entity.getChildModule() != null) {
                        dispatch = getChildDispatch(dispatch, entity.getChildModule());
                        if (dispatch != null) {
                            return dispatch.dispatch(context, entity, callback);
                        } else {
                            // callback
                        }
                    } else {
                        return dispatch.dispatch(context, entity, callback);
                    }
                } else {
                    // callback
                }
            } else {
                // callback
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        // callback
        return false;
    }

    /**
     * 获取子分发器
     */
    private AbsSchemeDispatch getChildDispatch(AbsSchemeDispatch mainDispatch, String[] childModule) {
        if (mainDispatch == null || childModule == null || childModule.length <= 0) {
            return null;
        }
        AbsSchemeDispatch dispatch = mainDispatch;
        for (String childName : childModule) {
            if (dispatch != null) {
                dispatch = dispatch.getChildDispatch(childName);
            } else {
                break;
            }
        }
        return dispatch;
    }
}
