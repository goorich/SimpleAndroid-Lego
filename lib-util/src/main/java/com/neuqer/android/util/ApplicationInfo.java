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

package com.neuqer.android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 应用信息
 *
 * @author techflowing
 * @since 2018/10/7 12:41 AM
 */
public final class ApplicationInfo {

    public static int getTargetSdkVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.applicationInfo.targetSdkVersion : -1;
    }

    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.versionName : "0.0";
    }

    static PackageInfo getPackageInfo(Context context) {
        try {
            return context != null ?
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0) : null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
