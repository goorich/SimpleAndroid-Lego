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

import android.app.Activity;

/**
 * 支付接口
 *
 * @author techflowing
 * @since 2019/1/23 10:31 PM
 */
public interface Pay<T extends PayInfo> {
    /**
     * 调起支付
     *
     * @param activity Activity
     * @param payInfo  支付信息
     * @param listener 支付结果回调
     */
    void callPay(Activity activity, T payInfo, PayResultListener listener);
}
