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

package com.neuqer.android.payment.alipay;

import java.util.HashMap;

/**
 * 支付错误码
 *
 * @author techflowing
 * @since 2019/1/23 10:43 PM
 */
public class AliPayCode {

    private static HashMap<Integer, String> sErrorMap;

    public static final int CODE_SUCCESS = 9000;
    public static final int CODE_HANDLING = 8000;
    public static final int CODE_FAIL = 4000;
    public static final int CODE_REPEAT = 000;
    public static final int CODE_CANCEL = 6001;
    public static final int CODE_NETWORK = 6002;
    public static final int CODE_UNKNOWN = 6004;

    private static final String TEXT_SUCCESS = "订单支付成功";
    private static final String TEXT_HANDLING = "正在处理中";
    private static final String TEXT_FAIL = "订单支付失败";
    private static final String TEXT_REPEAT = "重复请求";
    private static final String TEXT_CANCEL = "用户中途取消";
    private static final String TEXT_NETWORK = "网络连接出错";
    private static final String TEXT_UNKNOWN = "支付结果未知";
    private static final String TEXT_ERROR = "未知错误";

    static {
        sErrorMap = new HashMap<>();
        sErrorMap.put(CODE_SUCCESS, TEXT_SUCCESS);
        sErrorMap.put(CODE_HANDLING, TEXT_HANDLING);
        sErrorMap.put(CODE_FAIL, TEXT_FAIL);
        sErrorMap.put(CODE_REPEAT, TEXT_REPEAT);
        sErrorMap.put(CODE_CANCEL, TEXT_CANCEL);
        sErrorMap.put(CODE_NETWORK, TEXT_NETWORK);
        sErrorMap.put(CODE_UNKNOWN, TEXT_UNKNOWN);
    }

    public static String getTextByCode(int code) {
        String text = sErrorMap.get(code);
        if (text == null) {
            return TEXT_ERROR;
        }
        return text;
    }
}
