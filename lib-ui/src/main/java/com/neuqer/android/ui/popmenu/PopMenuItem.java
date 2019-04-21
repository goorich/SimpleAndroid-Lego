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

package com.neuqer.android.ui.popmenu;

import android.graphics.drawable.Drawable;

/**
 * 弹出菜单Item数据
 *
 * @author techflowing
 * @since 2018/10/21 11:16 PM
 */
public class PopMenuItem {

    private int mId;
    private String mTitle;
    private Drawable mDrawable;

    public PopMenuItem(int id, String title, Drawable drawable) {
        mId = id;
        mTitle = title;
        mDrawable = drawable;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }
}
