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
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限申请类
 *
 * @author techflowing
 * @since 2018/10/6 10:25 PM
 */
public class PermissionRequest {

    private Builder mBuilder;
    private Context mContext;
    private List<String> mPermissions;
    private List<String> mDenyPermissions;

    protected PermissionRequest(Builder builder) {
        if (builder == null) {
            return;
        }
        mBuilder = builder;
        mContext = builder.mContext;
        mPermissions = mBuilder.mPermissions;
        if (mPermissions == null || mPermissions.size() <= 0) {
            return;
        }
        mDenyPermissions = new ArrayList<>();
    }

    protected void start() {
        mDenyPermissions.clear();
        mDenyPermissions = PermissionUtil.filterDenyPermissions(mContext, mPermissions);
        if (mDenyPermissions.isEmpty()) {
            // 所有权限都已经允许
            callbackPermissionGranted();
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            notifySetting();
            return;
        }
        List<String> rationalePermission = new ArrayList<>();
        for (String permission : mDenyPermissions) {
            if (PermissionUtil.shouldShowRequestPermissionRationale(mContext, permission)) {
                rationalePermission.add(permission);
            }
        }
        if (rationalePermission.isEmpty()) {
            requestPermissionReal();
        } else {
            notifyRationale(rationalePermission);
        }
    }

    private void notifyRationale(List<String> rationalePermissions) {
        if (mBuilder.mRequestCallback != null) {
            mBuilder.mRationaleRender.doAction(rationalePermissions, new TypeListener.Process() {
                @Override
                public void onNext() {
                    requestPermissionReal();
                }

                @Override
                public void onCancel() {
                    callbackPermissionDenied(mDenyPermissions);
                }
            });
        } else {
            requestPermissionReal();
        }
    }

    private void notifySetting() {
        if (mBuilder.mSettingRender != null) {
            mBuilder.mSettingRender.doAction(mDenyPermissions, new TypeListener.Process() {
                @Override
                public void onNext() {
                    requestSettingReal();
                }

                @Override
                public void onCancel() {
                    callbackPermissionDenied(mDenyPermissions);
                }
            });
        } else {
            callbackPermissionDenied(mDenyPermissions);
        }
    }

    private void callbackPermissionGranted() {
        if (mBuilder.mRequestCallback != null) {
            mBuilder.mRequestCallback.onPermissionGranted();
        }
    }

    private void callbackPermissionDenied(List<String> deniedPermissions) {
        if (mBuilder.mRequestCallback != null) {
            mBuilder.mRequestCallback.onPermissionDenied(deniedPermissions);
        }
    }

    private void requestPermissionReal() {
        PermissionRequestActivity.startPermissionRequest(mContext, new PermissionRequestResponder() {
            @Override
            public void onPermissionResult(List<String> permissions) {
                mDenyPermissions = PermissionUtil.filterDenyPermissions(mContext, permissions);
                if (mDenyPermissions == null || mDenyPermissions.isEmpty()) {
                    callbackPermissionGranted();
                } else {
                    notifySetting();
                }
            }
        }, mPermissions);
    }

    private void requestSettingReal() {
        Intent intent = SettingIntent.getCanResolvedSettingIntent(mContext);
        if (intent != null) {
            PermissionSettingActivity.startPermissionSetting(mContext, new PermissionSettingResponder() {
                @Override
                public void onSettingResult() {
                    notifySettingResult();
                }
            });
        } else {
            notifySettingResult();
        }
    }

    private void notifySettingResult() {
        // 过滤被拒绝的权限
        mDenyPermissions.clear();
        mDenyPermissions = PermissionUtil.filterDenyPermissions(mContext, mPermissions);
        if (mDenyPermissions.isEmpty()) {
            callbackPermissionGranted();
        } else {
            callbackPermissionDenied(mDenyPermissions);
        }
    }

    /**
     * Builder，构建请求
     */
    public static class Builder {

        protected Context mContext;
        private List<String> mPermissions;
        private PermissionRationaleRender mRationaleRender;
        private PermissionRequestCallback mRequestCallback;
        private PermissionSettingRender mSettingRender;

        public Builder(Context context, String... permissions) {
            mContext = context;
            mPermissions = Arrays.asList(permissions);
        }

        public Builder setRationaleRender(PermissionRationaleRender rationaleRender) {
            mRationaleRender = rationaleRender;
            return this;
        }

        public Builder setRequestCallback(PermissionRequestCallback requestCallback) {
            mRequestCallback = requestCallback;
            return this;
        }

        public Builder setSettingRender(PermissionSettingRender settingRender) {
            mSettingRender = settingRender;
            return this;
        }

        public void request() {
            new PermissionRequest(this).start();
        }
    }

}
