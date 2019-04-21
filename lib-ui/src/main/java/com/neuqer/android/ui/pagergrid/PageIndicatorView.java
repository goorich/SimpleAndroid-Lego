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
 *
 *  There are modifications to the origin source code.
 *  Origin: https://github.com/Bigkoo/HorizontalGridPage
 */


package com.neuqer.android.ui.pagergrid;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 页面翻页指示器
 *
 * @author techflowing
 * @since 2018/10/18 10:56 PM
 */
public class PageIndicatorView extends LinearLayout {

    /** 指示器容器 */
    private ArrayList<View> indicatorViews;
    /** 指示器大小 */
    private int indicatorSize;
    /** 指示器间距 */
    private int[] margins;
    /** 指示器资源 */
    private int[] indicatorRes;

    public PageIndicatorView(Context context, int indicatorSize, int[] margins, int[] indicatorRes, int gravity) {
        super(context);
        this.indicatorSize = indicatorSize;
        this.margins = margins;
        this.indicatorRes = indicatorRes;
        setGravity(gravity);
        setOrientation(HORIZONTAL);//横向布局
    }

    /**
     * 初始化指示器，默认选中第一页
     *
     * @param count 指示器数量，等同页数
     */
    public void initIndicator(int count) {
        if (indicatorViews == null) {
            indicatorViews = new ArrayList<>();
        } else {
            indicatorViews.clear();
            removeAllViews();
        }

        LayoutParams params = new LayoutParams(indicatorSize, indicatorSize);
        params.setMargins(margins[0], margins[1], margins[2], margins[3]);
        for (int i = 0; i < count; i++) {
            View view = new View(getContext());
            view.setBackgroundResource(indicatorRes[0]);
            addView(view, params);
            indicatorViews.add(view);
        }
        //默认选中第一页
        if (indicatorViews.size() > 0) {
            indicatorViews.get(0).setBackgroundResource(indicatorRes[1]);
        }
    }

    /**
     * 设置选中页
     *
     * @param currentPage 当前页
     */
    public void update(int currentPage) {
        for (int i = 0; i < indicatorViews.size(); i++) {
            if (i == currentPage) {
                indicatorViews.get(i).setBackgroundResource(indicatorRes[1]);
            } else {
                indicatorViews.get(i).setBackgroundResource(indicatorRes[0]);
            }
        }
    }
}
