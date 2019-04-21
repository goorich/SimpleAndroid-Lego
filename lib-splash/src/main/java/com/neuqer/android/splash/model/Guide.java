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
 * 引导页面Model
 *
 * @author techflowing
 * @since 2019/1/1 6:09 PM
 */
public class Guide implements Splash {

    private int mImgRes;
    private boolean mShowInter;

    public Guide(int imgRes) {
        mImgRes = imgRes;
    }

    public int getImgRes() {
        return mImgRes;
    }

    public boolean isShowInter() {
        return mShowInter;
    }

    public void setShowInter(boolean showInter) {
        this.mShowInter = showInter;
    }
}
