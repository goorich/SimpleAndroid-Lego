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

package com.neuqer.android.splash;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.neuqer.android.splash.model.Ad;
import com.neuqer.android.splash.model.Guide;
import com.neuqer.android.splash.model.Slogan;
import com.neuqer.android.splash.model.Splash;
import com.neuqer.android.util.ApplicationInfo;
import com.neuqer.android.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页管理
 *
 * @author techflowing
 * @since 2019/1/1 9:14 PM
 */
public class SplashManager {

    public static final String SP_NAME = "sp_splash";
    public static final String KEY_FIRST_USE = "key_first_use:";
    public static final String KEY_SPLASH_AD = "key_splash_ad";


    private static int sSloganRes = R.drawable.bg_guide_three;
    private static List<Integer> mGuideRes;

    static {
        mGuideRes = new ArrayList<>();
        mGuideRes.add(R.drawable.bg_guide_one);
        mGuideRes.add(R.drawable.bg_guide_two);
    }

    /**
     * 获取引导页数据
     */
    public static List<Splash> getSplashData() {
        List<Splash> splashList = new ArrayList<>();
        if (isFirstUse() && mGuideRes != null && !mGuideRes.isEmpty()) {
            for (int i = 0; i < mGuideRes.size(); i++) {
                Guide guide = new Guide(mGuideRes.get(i));
                if (i == mGuideRes.size() - 1) {
                    guide.setShowInter(true);
                }
                splashList.add(guide);
            }
            updateFirstUseStatus(false);
            return splashList;
        }
        Ad ad;
        if ((ad = getSplashAd()) != null
                && ad.getStartTime() < System.currentTimeMillis()
                && ad.getEndTime() > System.currentTimeMillis()) {
            splashList.add(ad);
            deleteSplashAd();
            return splashList;
        }
        splashList.add(new Slogan(sSloganRes));
        return splashList;
    }

    /**
     * 存储开屏广告数据
     */
    public static void saveSplashAd(Ad ad) {
        if (ad == null) {
            return;
        }
        Gson gson = new Gson();
        SharedPreferencesUtil.getInstance(SP_NAME).put(KEY_SPLASH_AD, gson.toJson(ad));
    }

    /**
     * 更新首次使用状态
     */
    private static void updateFirstUseStatus(boolean first) {
        SharedPreferencesUtil.getInstance(SP_NAME).put(KEY_FIRST_USE
                + ApplicationInfo.getVersionName(SplashModule.getAppContext()), first);
    }

    /**
     * 当前版本是否首次使用
     */
    private static boolean isFirstUse() {
        return SharedPreferencesUtil.getInstance(SP_NAME).getBoolean(KEY_FIRST_USE
                + ApplicationInfo.getVersionName(SplashModule.getAppContext()), true);
    }

    /**
     * 获取开屏广告数据
     */
    private static Ad getSplashAd() {
        String json = SharedPreferencesUtil.getInstance(SP_NAME).getString(KEY_SPLASH_AD);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return new Gson().fromJson(json, Ad.class);
    }

    /**
     * 删除开屏广告数据
     */
    private static void deleteSplashAd() {
        SharedPreferencesUtil.getInstance(SP_NAME).remove(KEY_SPLASH_AD);
    }
}
