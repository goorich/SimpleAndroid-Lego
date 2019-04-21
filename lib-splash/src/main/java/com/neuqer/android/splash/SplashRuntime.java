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

/**
 * Runtime
 *
 * @author techflowing
 * @since 2019/1/1 4:35 PM
 */
public class SplashRuntime {

    static ISplashContext sSplashContext;

    public static ISplashContext getContext() {
        return sSplashContext;
    }
}
