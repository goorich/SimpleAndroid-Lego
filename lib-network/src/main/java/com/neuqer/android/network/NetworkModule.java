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

package com.neuqer.android.network;

import android.app.Application;

import com.neuqer.android.runtime.IModuleApplication;

/**
 * 网络库 Module Application
 *
 * @version v1.0.0
 * @since 2019-07-20 16:31
 */
public class NetworkModule implements IModuleApplication {

    @Override
    public void onApplicationAttachBaseContext(Application application) {

    }

    @Override
    public void onApplicationCreate(Application application) {
    }
}
