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

package com.neuqer.android.network.alone;

import com.neuqer.android.network.global.InitException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 单独配置请求，一般不建议使用，因此不做过多设计
 *
 * @author techflowing
 * @since 2018/10/14 6:11 PM
 */
public class Alone {

    private Retrofit.Builder mBuilder;
    private OkHttpClient.Builder mClientBuilder;

    public Alone() {
    }

    public Alone config(Retrofit.Builder builder, OkHttpClient.Builder clientBuilder) {
        mBuilder = builder;
        mClientBuilder = clientBuilder;
        return this;
    }

    public <K> K createService(Class<K> service) {
        if (mBuilder == null) {
            throw new InitException("未进行配置，请先调用 config() 方法");
        }
        mBuilder.client(mClientBuilder.build());
        return mBuilder.build().create(service);
    }
}
