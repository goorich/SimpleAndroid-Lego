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

package com.neuqer.android.network;

import android.app.Application;
import android.content.Context;

import retrofit2.Retrofit;
import com.neuqer.android.network.alone.Alone;
import com.neuqer.android.network.global.Global;
import com.neuqer.android.network.global.GlobalRetrofit;

/**
 * Client,对外暴露的入口
 *
 * @author techflowing
 * @since 2018/10/14 4:53 PM
 */
public class HttpClient {

    private Application mApplication;

    /**
     * 私有构造方法
     */
    private HttpClient() {

    }

    /**
     * 单例
     */
    public static HttpClient getInstance() {
        return Holder.sInstance;
    }

    /**
     * 单独配置请求
     */
    public static Alone alone() {
        return new Alone();
    }

    /**
     * Application OnCreate 全局配置
     */
    public void onApplicationCreate(Application application) {
        mApplication = application;
    }

    /**
     * 全局配置HttpClient
     */
    public void globalConfig(Retrofit.Builder builder) {
        GlobalRetrofit.getInstance().init(builder);
    }

    /**
     * 使用全局参数创建请求
     */
    /**
     * 使用全局参数创建请求
     */
    public <K> K createService(Class<K> service) {
        return Global.getInstance().createService(service);
    }

    /**
     * 获取Context
     */
    Context getContext() {
        return mApplication;
    }

    /**
     * 静态内部类-单例实现
     */
    private static class Holder {
        private static final HttpClient sInstance = new HttpClient();
    }
}
