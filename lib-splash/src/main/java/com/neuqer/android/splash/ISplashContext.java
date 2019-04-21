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

package com.neuqer.android.splash;

import android.content.Context;

/**
 * Splash引导Ioc接口
 *
 * @author techflowing
 * @since 2019/1/1 8:16 PM
 */
public interface ISplashContext {

    /**
     * 进入主页面
     */
    void startHomeActivity(Context context);
}
