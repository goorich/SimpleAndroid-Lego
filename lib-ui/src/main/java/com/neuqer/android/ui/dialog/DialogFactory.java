/*
 * Copyright 2018. techflowing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neuqer.android.ui.dialog;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Dialog创建类
 *
 * @author techflowing
 * @since 2018/9/24 下午7:48
 */
public class DialogFactory {

    /** 上下文 */
    private Context mContext;

    /**
     * 构造方法
     */
    private DialogFactory(Context context) {
        mContext = context;
    }


    /**
     * @see #showLoadingDialog(String)
     */
    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    /**
     * @see #showLoadingDialog(String)
     */
    public void showLoadingDialog(@StringRes int hint) {
        showLoadingDialog(mContext.getString(hint));
    }

    /**
     * 创建LoadingDialog
     *
     * @param hint 提示文字
     */
    public void showLoadingDialog(String hint) {
        LoadingDialog dialog = new LoadingDialog(mContext);
        dialog.setHintText(hint).show();
    }

    /**
     * @see #showAlertDialog(String, String, String, View.OnClickListener, String, View.OnClickListener, String, View.OnClickListener)
     */
    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                @StringRes int positiveText, View.OnClickListener positiveListener,
                                @StringRes int negativeText, View.OnClickListener negativeListener) {
        showAlertDialog(mContext.getString(title),
                mContext.getString(message),
                mContext.getString(positiveText), positiveListener,
                mContext.getString(negativeText), negativeListener, null, null);
    }

    /**
     * @see #showAlertDialog(String, String, String, View.OnClickListener, String, View.OnClickListener, String, View.OnClickListener)
     */
    public void showAlertDialog(String title, String message,
                                String positiveText, View.OnClickListener positiveListener,
                                String negativeText, View.OnClickListener negativeListener) {
        showAlertDialog(title, message, positiveText, positiveListener,
                negativeText, negativeListener, null, null);
    }

    /**
     * @see #showAlertDialog(String, String, String, View.OnClickListener, String, View.OnClickListener, String, View.OnClickListener)
     */
    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                @StringRes int positiveText, View.OnClickListener positiveListener,
                                @StringRes int negativeText, View.OnClickListener negativeListener,
                                @StringRes int neutralText, View.OnClickListener neutralListener) {
        showAlertDialog(mContext.getString(title),
                mContext.getString(message),
                mContext.getString(positiveText), positiveListener,
                mContext.getString(negativeText), negativeListener,
                mContext.getString(neutralText), neutralListener);
    }

    /**
     * 显示AlertDialog
     *
     * @param title            标题
     * @param message          内容
     * @param positiveText     确定文字
     * @param positiveListener 确定点击事件
     * @param negativeText     取消文字
     * @param negativeListener 取消点击事件
     * @param neutralText      中立文字
     * @param neutralListener  中立点击事件
     */
    public void showAlertDialog(String title, String message,
                                String positiveText, View.OnClickListener positiveListener,
                                String negativeText, View.OnClickListener negativeListener,
                                String neutralText, View.OnClickListener neutralListener) {
        AlertDialog dialog = new AlertDialog(mContext);
        dialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton(negativeText, negativeListener)
                .setPositiveButton(positiveText, positiveListener)
                .setNeutralButton(neutralText, neutralListener)
                .show();
    }

    /**
     * 获取Factory实例
     *
     * @param context 上下文
     */
    public static DialogFactory get(Context context) {
        return new DialogFactory(context);
    }
}
