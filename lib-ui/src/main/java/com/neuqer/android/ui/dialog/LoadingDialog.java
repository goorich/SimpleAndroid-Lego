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
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.neuqer.android.ui.R;

/**
 * Loading加载Dialog
 *
 * @author techflowing
 * @since 2018/9/24 下午7:47
 */
public class LoadingDialog extends AbsDialog {

    private TextView mHintText;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.dialog, Gravity.CENTER,
                context.getResources().getDimensionPixelOffset(R.dimen.dialog_loading_width),
                WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public LoadingDialog setHintText(String text) {
        if (!TextUtils.isEmpty(text) && mHintText != null) {
            mHintText.setText(text);
        }
        return this;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void init(View rootView) {
        mHintText = rootView.findViewById(R.id.txt_hint);
    }
}
