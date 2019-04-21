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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 权限设置代理页面
 *
 * @author techflowing
 * @since 2018/10/8 1:12 AM
 */
public class PermissionSettingActivity extends AgentActivity {

    private static final int REQUEST_CODE = 0x1111;
    private static PermissionSettingResponder sSettingResponder;

    public static void startPermissionSetting(Context ctx, PermissionSettingResponder responder) {
        sSettingResponder = responder;
        Intent intent = new Intent(ctx, PermissionSettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent settingIntent = SettingIntent.getCanResolvedSettingIntent(this);
        if (settingIntent != null) {
            startActivityForResult(settingIntent, REQUEST_CODE);
        } else {
            // 无法打开设置界面，回调以便继续后面流程
            notifySettingResult();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            notifySettingResult();
        }
    }

    private void notifySettingResult() {
        if (sSettingResponder != null) {
            sSettingResponder.onSettingResult();
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        sSettingResponder = null;
    }
}
