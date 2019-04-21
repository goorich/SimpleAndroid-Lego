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
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 横向滑动页面
 *
 * @author techflowing
 * @since 2018/10/18 10:56 PM
 */
public class HorizontalGridPage extends LinearLayout {

    PageGridView gridView;
    PageIndicatorView indicatorView;

    public HorizontalGridPage(Context context) {
        this(context, null);
    }

    public HorizontalGridPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalGridPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     *
     * @param builder 参数构建器
     */
    public void init(PageBuilder builder) {
        // 纵向排列
        setOrientation(LinearLayout.VERTICAL);

        if (builder == null) {
            builder = new PageBuilder.Builder().build();
        }

        int[] grid = builder.getGrid();
        int swipePercent = builder.getSwipePercent();
        gridView = new PageGridView(getContext(), grid, swipePercent);

        int indicatorSize = dip2px(builder.getIndicatorSize());
        int[] margins = {dip2px(builder.getIndicatorMargins()[0]), dip2px(builder.getIndicatorMargins()[1]),
                dip2px(builder.getIndicatorMargins()[2]), dip2px(builder.getIndicatorMargins()[3])};
        int[] indicatorRes = builder.getIndicatorRes();
        int gravity = builder.getIndicatorGravity();
        indicatorView = new PageIndicatorView(getContext(), indicatorSize, margins, indicatorRes, gravity);
        gridView.setIndicator(indicatorView);
        removeAllViews();
        addView(gridView);
        if (builder.isShowIndicator()) {
            addView(indicatorView);
        } else {
            removeView(indicatorView);
        }
    }

    /**
     * 设置Adapter
     *
     * @param adapter 设置的Adapter
     */
    public <T extends PageGridAdapter> void setAdapter(T adapter) {
        gridView.setAdapter(adapter);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 要转换的dp值
     */
    private int dip2px(int dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

