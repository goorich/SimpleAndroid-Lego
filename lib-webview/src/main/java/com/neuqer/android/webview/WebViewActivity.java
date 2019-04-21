/*
 * Copyright 2018. techflowing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neuqer.android.webview;


import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IWebLayout;
import com.neuqer.android.runtime.base.activity.AbsActivity;

/**
 * 带有WebView的Activity，基于AgentWeb实现
 *
 * @author techflowing
 * @since 2018/11/20 11:16 PM
 */
public abstract class WebViewActivity extends AbsActivity {

    /** WebView容器 */
    private ViewGroup mWebViewParent;
    /** AgentWebView */
    private AgentWeb.PreAgentWeb mPreAgentWeb;
    /** AgentWebView */
    private AgentWeb mAgentWeb;

    @Override
    protected void initView() {
        mWebViewParent = findViewById(getWebViewParent());
        if (mWebViewParent == null) {
            throw new IllegalStateException("WebView容器不能为空");
        }
        mPreAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebViewParent, new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                        .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(getWebLayout())
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready();
    }

    /**
     * 加载URL地址
     *
     * @param url url地址
     */
    protected void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (mAgentWeb == null) {
            mAgentWeb = mPreAgentWeb.go(url);
        } else {
            mAgentWeb.getUrlLoader().loadUrl(url);
        }
    }

    /**
     * 获取WebViewClient，子类按需重写
     */
    protected WebViewClient getWebViewClient() {
        return new WebViewClient();
    }

    /**
     * 获取WebChromeClient，子类按需重写
     */
    protected WebChromeClient getWebChromeClient() {
        return new WebChromeClient();
    }

    /**
     * 获取WebLayout，子类可重写，比如添加下拉刷新等操作
     */
    private IWebLayout getWebLayout() {
        return new SimpleWebLayout(this);
    }

    /**
     * 获取WebView容器
     */
    @IdRes
    protected abstract int getWebViewParent();

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
