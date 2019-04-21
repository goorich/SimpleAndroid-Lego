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
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.neuqer.android.runtime.base.fragment.AbsFragment;
import com.neuqer.android.splash.R;
import com.neuqer.android.splash.R2;

import java.io.File;

import butterknife.BindView;

/**
 * 开屏广告页面
 *
 * @author techflowing
 * @since 2019/1/1 5:54 PM
 */
public class SplashAdFragment extends AbsFragment {

    public static final String KEY_IMG_PATH = "key_img_path";
    /** 广告倒计时2秒 */
    private static final int COUNT_TIME = 2 * 1000;

    @BindView(R2.id.img_content)
    ImageView contentImg;
    @BindView(R2.id.btn_skip)
    Button skipBtn;

    private String mImgPath;
    private OnEnterHomeListener mEnterHomeListener;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_splash_ad;
    }

    @Override
    protected void initVariable() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mImgPath = bundle.getString(KEY_IMG_PATH);
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(mImgPath)) {
            Glide.with(this).load(new File(mImgPath)).into(contentImg);
        }
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEnterHomeListener != null) {
                    mEnterHomeListener.enterHome();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mEnterHomeListener != null) {
                    mEnterHomeListener.enterHome();
                }
            }
        }, COUNT_TIME);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEnterHomeListener) {
            mEnterHomeListener = (OnEnterHomeListener) context;
        }
    }
}
