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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.just.agentweb.IWebLayout;

/**
 * 简单WebView容器
 *
 * @author techflowing
 * @since 2018/11/21 12:37 AM
 */
public class SimpleWebLayout implements IWebLayout<WebView, FrameLayout> {

    private FrameLayout mRoot;
    private WebView mWebView;

    public SimpleWebLayout(Context context) {
        mRoot = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.simple_web_layout, null);
        mWebView = mRoot.findViewById(R.id.web_view);
    }

    @NonNull
    @Override
    public FrameLayout getLayout() {
        return mRoot;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }
}
