package com.neuqer.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author chengkun
 * @since 2018/11/3 21:13
 */
public class ToastUtil {

    private static Toast sToast;
    private static boolean sEnableShow = true;

    private ToastUtil() {

    }

    /**
     * 全局控制toast展示
     *
     * @param enableShow 是否允许展示
     */
    public static void setEnableShow(boolean enableShow) {
        sEnableShow = enableShow;
    }


    public static void show(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int resId) {
        show(context, resId, Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    public static void show(Context context, CharSequence msg, int duration) {
        if (sEnableShow) {
            if (sToast == null) {
                sToast = Toast.makeText(context, msg, duration);
            } else {
                sToast.setText(msg);
            }

            if (context instanceof Activity) {
                showOnUiThread((Activity) context);
            } else {
                sToast.show();
            }
        }
    }

    @SuppressLint("ShowToast")
    public static void show(Context context, int resId, int duration) {
        if (sEnableShow) {
            if (sToast == null) {
                sToast = Toast.makeText(context, resId, duration);
            } else {
                sToast.setText(resId);
            }

            if (context instanceof Activity) {
                showOnUiThread((Activity) context);
            } else {
                sToast.show();
            }
        }
    }

    private static void showOnUiThread(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sToast.show();
            }
        });
    }

    /**
     * 取消toast
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
