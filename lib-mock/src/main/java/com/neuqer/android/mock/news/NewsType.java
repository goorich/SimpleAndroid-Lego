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

package com.neuqer.android.mock.news;

import java.util.HashMap;

/**
 * 资讯Item样式 常量定义
 *
 * @author techflowing
 * @since 2018/10/10 12:08 AM
 */
public enum NewsType {

    NO_MATCH("no_match"),                                            // 非法数据
    TEXT("text_only"),                                               // 纯文本
    ONE_IMG_LEFT("one_img_left"),                                    // 左图右文
    THREE_IMG("three_img");                                          // 三图


    private static final HashMap<String, NewsType> sMap = new HashMap<>();
    private String mName;

    static {
        for (NewsType layout : NewsType.values()) {
            sMap.put(layout.mName, layout);
        }
    }

    NewsType(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public static NewsType getLayout(String name) {
        NewsType layout = sMap.get(name);
        return layout == null ? NO_MATCH : layout;
    }

    public static boolean isSupportLayout(String name) {
        return getLayout(name) != NO_MATCH;
    }
}
