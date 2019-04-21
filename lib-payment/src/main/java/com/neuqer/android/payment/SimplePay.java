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

package com.neuqer.android.payment;

import android.app.Activity;

import com.alipay.sdk.app.EnvUtils;
import com.neuqer.android.payment.alipay.AliPay;
import com.neuqer.android.payment.alipay.AliPayInfo;
import com.neuqer.android.payment.pay.PayResultListener;

/**
 * 支付入口
 *
 * @author techflowing
 * @since 2019/1/22 11:05 PM
 */
public class SimplePay {

    /**
     * 调起支付宝支付
     *
     * @param activity  Activity
     * @param orderInfo 订单信息
     * @param listener  结果回调
     */
    public static void aipay(Activity activity, String orderInfo, PayResultListener listener) {
        // 使用沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        new AliPay().callPay(activity, new AliPayInfo(orderInfo), listener);
    }
}
