/*
 * Copyright 2019. techflowing
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

package com.neuqer.android.splash.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.neuqer.android.runtime.base.fragment.AbsFragment;
import com.neuqer.android.splash.R;
import com.neuqer.android.splash.R2;

import butterknife.BindView;

/**
 * 引导页面
 *
 * @author techflowing
 * @since 2019/1/1 5:34 PM
 */
public class GuideFragment extends AbsFragment {

    public static final String KEY_IMG_RES = "key_img_res";
    public static final String KEY_SHOW_ENTER = "show_enter";

    @BindView(R2.id.img_content)
    ImageView contentImg;
    @BindView(R2.id.btn_enter)
    Button enterBtn;

    @DrawableRes
    private int mImgRes;
    private boolean mShowEnter;
    private OnEnterHomeListener mEnterHomeListener;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initVariable() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mImgRes = bundle.getInt(KEY_IMG_RES);
        mShowEnter = bundle.getBoolean(KEY_SHOW_ENTER);
    }

    @Override
    protected void initView() {
        contentImg.setImageResource(mImgRes);
        if (mShowEnter) {
            enterBtn.setVisibility(View.VISIBLE);
            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEnterHomeListener != null) {
                        mEnterHomeListener.enterHome();
                    }
                }
            });
        } else {
            enterBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEnterHomeListener) {
            mEnterHomeListener = (OnEnterHomeListener) context;
        }
    }
}
