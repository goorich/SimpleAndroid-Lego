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

package com.neuqer.android.payment.pay;

/**
 * 支付结果回调
 *
 * @author techflowing
 * @since 2019/1/23 10:28 PM
 */
public interface PayResultListener {

    /**
     * 支付成功
     */
    void onSuccess(PayType type, String resultInfo);

    /**
     * 正在处理中，支付结果未知，需要根据Server状态进行查询确认
     */
    void onProcess(PayType type);

    /**
     * 支付失败
     */
    void onFailed(PayType type, int errCode, String errMsg);

    /**
     * 取消支付
     */
    void onCancel(PayType type);
}
