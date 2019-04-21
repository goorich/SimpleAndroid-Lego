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

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.neuqer.android.ui.R;

/**
 * 自定义Dialog基类
 *
 * @author techflowing
 * @since 2018/9/24 上午1:01
 */
public abstract class AbsDialog extends Dialog {

    /** Context */
    private Context mContext;
    /** 自定义View */
    private View mRootView;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public AbsDialog(@NonNull Context context) {
        this(context, R.style.dialog, Gravity.CENTER, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 构造方法
     *
     * @param context    上下文
     * @param themeResId style资源
     */
    protected AbsDialog(@NonNull Context context, int themeResId) {
        this(context, themeResId, Gravity.CENTER, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 构造方法
     *
     * @param context    上下文
     * @param themeResId style资源
     * @param gravity    {@link Gravity}
     * @param width      dialog 宽度
     * @param height     dialog高度
     */
    protected AbsDialog(@NonNull Context context, @StyleRes int themeResId, int gravity, int width, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mRootView = LayoutInflater.from(context).inflate(getLayoutRes(), null);
        setContentView(mRootView);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            dialogWindow.setWindowAnimations(-1);
            dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            dialogWindow.setGravity(gravity);
            WindowManager.LayoutParams p = dialogWindow.getAttributes();
            p.width = width;
            p.height = height;
            dialogWindow.setAttributes(p);
        }
        init(mRootView);
    }


    @Override
    public void show() {
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }
        if (!isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    /**
     * 自定义Dialog布局资源
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 初始化
     *
     * @param rootView 根View
     */
    protected abstract void init(View rootView);
}


