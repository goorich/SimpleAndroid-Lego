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

package com.neuqer.android.permission;

import android.content.Context;

import java.util.List;

/**
 * 权限申请请求抽象
 *
 * @author techflowing
 * @since 2018/10/6 11:55 PM
 */
interface PermissionRequestCaller {

    /**
     * 申请权限
     *
     * @param context    上下文
     * @param responder  权限申请结果响应器
     * @param permission 权限列表
     */
    void requestPermission(Context context, PermissionRequestResponder responder, List<String> permission);

}
