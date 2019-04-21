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

package com.neuqer.android.network.global;

import java.util.HashMap;

import retrofit2.Retrofit;

/**
 * 全局Retrofit类
 *
 * @author techflowing
 * @since 2018/10/14 6:59 PM
 */
public class GlobalRetrofit {

    private Retrofit mRetrofit;
    private Retrofit.Builder mBuilder;
    private HashMap<String, Object> mServiceCache;

    private GlobalRetrofit() {
        mServiceCache = new HashMap<>();
    }

    public void init(Retrofit.Builder builder) {
        if (mBuilder != null) {
            throw new InitException("重复初始化");
        }
        mBuilder = builder;
    }

    public static GlobalRetrofit getInstance() {
        return Holder.sInstance;
    }

    private Retrofit getRetrofit() {
        if (mRetrofit != null) {
            return mRetrofit;
        }
        if (mBuilder == null) {
            throw new InitException("未进行初始化");
        }
        mRetrofit = mBuilder.build();
        return mRetrofit;
    }

    /**
     * 使用全局参数创建请求
     */
    public <K> K createService(Class<K> service) {
        K retrofitService = (K) mServiceCache.get(service.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = getRetrofit().create(service);
            mServiceCache.put(service.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    private static class Holder {
        private static GlobalRetrofit sInstance = new GlobalRetrofit();
    }
}
