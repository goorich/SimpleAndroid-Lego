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

package com.neuqer.android.runtime.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.neuqer.android.runtime.R;
import com.neuqer.android.runtime.base.BackHandlerHelper;

import butterknife.ButterKnife;

/**
 * Activity抽象类
 *
 * @author techflowing
 * @since 2018/9/21 上午12:27
 */
abstract class AbsBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        getWindow().setBackgroundDrawableResource(R.drawable.bg_activity_white);
        ButterKnife.bind(this);
        initVariable();
        initView();
        loadData();
    }

    /**
     * 获取内容View
     * 子类可重写此方法，实现不同样式的基类Activity
     */
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(getLayoutRes(), null);
    }

    /**
     * 获取Activity LayoutRes
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 初始化变量
     */
    protected abstract void initVariable();

    /**
     * 初始化View，挂载相关事件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected void loadData() {

    }

    /**
     * 获取页面标题
     */
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (!isFragmentHandleBackPressed()) {
            super.onBackPressed();
        }
    }

    /**
     * Fragment是否处理了返回键
     */
    protected boolean isFragmentHandleBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }
}
