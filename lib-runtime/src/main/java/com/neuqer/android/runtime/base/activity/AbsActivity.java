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

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.neuqer.android.runtime.R;


/**
 * Activity 基类
 *
 * @author techflowing
 * @since 2018/9/21 上午12:40
 */
public abstract class AbsActivity extends AbsBaseActivity {

    /** Toolbar */
    private Toolbar mToolbar;

    @Override
    protected View getContentView() {
        LayoutInflater inflater = getLayoutInflater();
        View container = inflater.inflate(R.layout.activity_base, null);
        FrameLayout content = container.findViewById(R.id.content);
        content.addView(inflater.inflate(getLayoutRes(), null));
        initToolbar(container);
        return container;
    }


    /**
     * 初始化Toolbar.
     */
    private void initToolbar(View root) {
        if (!toolbarEnable()) {
            return;
        }
        mToolbar = root.findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getActivityTitle());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && backButtonEnable()) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackButtonClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 设置Activity标题
     */
    protected void setActivityTitle(CharSequence title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    /**
     * 获取当前页面的Toolbar.
     */
    @Nullable
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 是否有返回按钮，默认无
     */
    protected boolean backButtonEnable() {
        return false;
    }

    /**
     * 返回按钮点击事件
     */
    protected void onBackButtonClick() {
        finish();
    }

    /**
     * 是否有Toolbar
     */
    protected boolean toolbarEnable() {
        return true;
    }
}
