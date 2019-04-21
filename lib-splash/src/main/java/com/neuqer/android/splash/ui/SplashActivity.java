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

import android.support.v4.view.ViewPager;

import com.neuqer.android.runtime.base.activity.AbsActivity;
import com.neuqer.android.splash.R;
import com.neuqer.android.splash.R2;
import com.neuqer.android.splash.SplashManager;
import com.neuqer.android.splash.SplashRuntime;
import com.neuqer.android.splash.adapter.SplashAdapter;

import butterknife.BindView;

/**
 * 启动页
 *
 * @author techflowing
 * @since 2019/1/1 5:25 PM
 */
public class SplashActivity extends AbsActivity implements OnEnterHomeListener {


    @BindView(R2.id.view_pager)
    ViewPager viewPager;

    private SplashAdapter mSplashAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariable() {
        mSplashAdapter = new SplashAdapter(getSupportFragmentManager(), SplashManager.getSplashData());
    }

    @Override
    protected void initView() {
        viewPager.setAdapter(mSplashAdapter);
    }

    @Override
    public void enterHome() {
        SplashRuntime.getContext().startHomeActivity(SplashActivity.this);
        finish();
    }

    @Override
    protected boolean toolbarEnable() {
        return false;
    }
}
