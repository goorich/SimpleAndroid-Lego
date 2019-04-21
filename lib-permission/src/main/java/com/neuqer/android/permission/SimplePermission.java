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
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.List;

/**
 * 对外暴露的类
 *
 * @author techflowing
 * @since 2018/10/8 10:15 PM
 */
public class SimplePermission extends PermissionRequest {

    private SimplePermission(Builder builder) {
        super(builder);
    }

    public static class Builder extends PermissionRequest.Builder {

        private String mRationaleMessage;
        private String mRationaleBtn;
        private String mSettingMessage;
        private String mSettingPositiveBtn;
        private String mSettingNegativeBtn;

        public Builder(Context context, String... permissions) {
            super(context, permissions);
        }

        public Builder setRationaleMessage(String rationaleMessage) {
            mRationaleMessage = rationaleMessage;
            return this;
        }

        public Builder setRationaleBtn(String rationaleBtn) {
            mRationaleBtn = rationaleBtn;
            return this;
        }

        public Builder setSettingMessage(String settingMessage) {
            mSettingMessage = settingMessage;
            return this;
        }

        public Builder setSettingPositiveBtn(String settingPositiveBtn) {
            mSettingPositiveBtn = settingPositiveBtn;
            return this;
        }

        public Builder setSettingNegativeBtn(String settingNegativeBtn) {
            mSettingNegativeBtn = settingNegativeBtn;
            return this;
        }

        public void request() {
            // 默认Rationale Dialog
            if (!TextUtils.isEmpty(mRationaleMessage)) {
                setRationaleRender(new PermissionRationaleRender() {
                    @Override
                    public void doAction(List<String> params, final Process process) {
                        String positiveBtn = TextUtils.isEmpty(mRationaleBtn) ?
                                mContext.getString(R.string.permission_rationale_positive_btn) : mRationaleBtn;
                        new AlertDialog.Builder(mContext)
                                .setMessage(mRationaleMessage)
                                .setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        process.onNext();
                                    }
                                })
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        process.onCancel();
                                    }
                                }).show();
                    }
                });
            }
            // 权限设置页面
            if (!TextUtils.isEmpty(mSettingMessage)) {
                setSettingRender(new PermissionSettingRender() {
                    @Override
                    public void doAction(List<String> params, final Process process) {
                        String positiveBtn = TextUtils.isEmpty(mSettingPositiveBtn) ?
                                mContext.getString(R.string.permission_setting_positive_btn) : mSettingPositiveBtn;
                        String negativeBtn = TextUtils.isEmpty(mSettingNegativeBtn) ?
                                mContext.getString(R.string.permission_setting_negative_btn) : mSettingNegativeBtn;
                        new AlertDialog.Builder(mContext)
                                .setMessage(mSettingMessage)
                                .setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        process.onNext();
                                    }
                                })
                                .setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        process.onCancel();
                                    }
                                })
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        process.onCancel();
                                    }
                                }).show();
                    }
                });
            }
            // 发起请求
            new SimplePermission(this).start();
        }

    }

}
