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
 *
 *  There are modifications to the origin source code.
 *  Origin: https://github.com/SupLuo/HaloPermission/
 */

package com.neuqer.android.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 各大手机厂商权限设置界面打开方法
 * <p>
 * 参考自GitHub
 *
 * @author techflowing
 * @since 2018/10/8 12:42 AM
 */
class SettingIntent {

    private static final String MF_HUAWEI = "huawei";
    private static final String MF_XIAOMI = "xiaomi";
    private static final String MF_OPPO = "oppo";
    private static final String MF_VIVO = "vivo";
    private static final String MF_SAMSUNG = "samsung";
    private static final String MF_MEIZU = "meizu";
    private static final String MF_SMARTISAN = "smartisan";
    private static final String MF_SONY = "sony";
    private static final String MF_LETV = "letv";
    private static final String MF_LG = "lg";

    public static Intent obtainSettingIntent(Context ctx) {
        // 获取设备制造商,缺陷：如果用于将手机刷机，比如使用华为的手机刷机小米的系统，这种情况得到的判断可能有误
        String mf = Build.MANUFACTURER.toLowerCase();
        if (mf.contains(MF_HUAWEI)) {
            return huawei(ctx);
        } else if (mf.contains(MF_XIAOMI)) {
            return xiaomi(ctx);
        } else if (mf.contains(MF_OPPO)) {
            return oppo(ctx);
        } else if (mf.contains(MF_VIVO)) {
            return vivo(ctx);
        } else if (mf.contains(MF_SAMSUNG)) {
            return samsung(ctx);
        } else if (mf.contains(MF_MEIZU)) {
            return meizu(ctx);
        } else if (mf.contains(MF_SMARTISAN)) {
            return smartisan(ctx);
        } else if (mf.contains(MF_SONY)) {
            return sony(ctx);
        } else if (mf.contains(MF_LETV)) {
            return letv(ctx);
        } else if (mf.contains(MF_LG)) {
            return lg(ctx);
        } else {
            return defaultIntent(ctx);
        }
    }


    /**
     * 获取能够被正常处理的SettingIntent
     *
     * @return 校验之后可以被处理的的Intent
     */
    public static Intent getCanResolvedSettingIntent(Context ctx) {
        Intent settingIntent = SettingIntent.obtainSettingIntent(ctx);
        if (settingIntent.resolveActivity(ctx.getPackageManager()) != null) {
            return settingIntent;
        }

        settingIntent = SettingIntent.defaultIntent(ctx);
        if (settingIntent.resolveActivity(ctx.getPackageManager()) != null) {
            return settingIntent;
        }

        settingIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        if (settingIntent.resolveActivity(ctx.getPackageManager()) != null) {
            return settingIntent;
        }
        return null;
    }

    /**
     * 默认Intent
     */
    private static Intent defaultIntent(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    private static Intent huawei(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return defaultIntent(ctx);
        }
        Intent intent = new Intent();
        intent.putExtra("packageName", ctx.getPackageName());
        intent.setClassName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        return intent;
    }


    private static Intent xiaomi(Context ctx) {
        String miuiVersion = getMIUIVersion().toUpperCase();
        switch (miuiVersion) {
            case "V6":
            case "V7":
                return miuiV6_7(ctx);
            case "V8":
            case "V9":
                return miuiV8_9(ctx);
            default:
                return defaultIntent(ctx);
        }
    }

    /**
     * 获取MIUI系统版本
     */
    private static String getMIUIVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        return version != null ? version : "";
    }

    private static Intent miuiV6_7(Context ctx) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", ctx.getPackageName());
        return intent;
    }

    private static Intent miuiV8_9(Context ctx) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", ctx.getPackageName());
        return intent;
    }

    private static Intent vivo(Context ctx) {
        Intent intent = new Intent();
        intent.putExtra("packagename", ctx.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            intent.setClassName("com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        } else {
            intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
        }
        return intent;
    }

    private static Intent oppo(Context ctx) {
        return defaultIntent(ctx);
    }

    private static Intent meizu(Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            return defaultIntent(context);
        }

        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        return intent;
    }

    private static Intent smartisan(Context context) {
        return defaultIntent(context);
    }


    private static Intent samsung(Context context) {
        return defaultIntent(context);
    }

    /**
     * 索尼
     */
    private static Intent sony(Context ctx) {
        Intent intent = new Intent();
        intent.putExtra("packageName", ctx.getPackageName());
        intent.setClassName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        return intent;
    }

    /**
     * 乐视
     */
    private static Intent letv(Context ctx) {
        Intent intent = new Intent();
        intent.putExtra("packageName", ctx.getPackageName());
        intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        return intent;
    }

    private static Intent lg(Context ctx) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.putExtra("packageName", ctx.getPackageName());
        intent.setClassName("com.android.settings", "com.android.settings.Settings'$'AccessLockSummaryActivity");
        return intent;
    }

    private static Intent qihoo360(Context ctx) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.putExtra("packageName", ctx.getPackageName());
        intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        return intent;
    }

    /**
     * Returns a SystemProperty
     *
     * @param propName The Property to retrieve
     * @return The Property, or NULL if not found
     * 此方法来源于[https://searchcode.com/codesearch/view/41537878/]
     */
    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {

            }
        }
        return line;
    }
}
