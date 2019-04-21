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

package com.neuqer.android.scheme;

import android.content.Context;
import android.text.TextUtils;

/**
 * Scheme分发统一工具类
 *
 * @author techflowing
 * @since 2018/11/22 11:05 PM
 */
public class Scheme {

    /**
     * Scheme分发
     *
     * @param context 上下文
     * @param scheme  Scheme
     */
    public static void dispatch(Context context, String scheme) {
        dispatch(context, scheme, null);
    }

    /**
     * Scheme分发
     *
     * @param context  上下文
     * @param scheme   Scheme
     * @param callback 接口回调
     */
    public static void dispatch(Context context, String scheme, SchemeCallback callback) {
        if (context == null || TextUtils.isEmpty(scheme)) {
            return;
        }
        MainSchemeDispatch dispatch = new MainSchemeDispatch(context);
        dispatch.dispatch(scheme, callback);
    }
}
