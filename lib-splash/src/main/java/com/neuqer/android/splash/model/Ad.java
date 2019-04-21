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

package com.neuqer.android.splash.model;

/**
 * 广告页面数据model
 *
 * @author techflowing
 * @since 2019/1/1 6:13 PM
 */
public class Ad implements Splash {

    private long mStartTime;
    private long mEndTime;
    private String mImgPath;

    public Ad(long startTime, long endTime, String imgPath) {
        mStartTime = startTime;
        mEndTime = endTime;
        mImgPath = imgPath;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public String getImgPath() {
        return mImgPath;
    }
}
