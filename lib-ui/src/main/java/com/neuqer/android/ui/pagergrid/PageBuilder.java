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

import android.view.Gravity;

/**
 * 页面配置文件，参数构建器
 *
 * @author techflowing
 * @since 2018/10/18 10:56 PM
 */
public class PageBuilder {

    /** 指示器的大小（dp） */
    private int indicatorSize;
    /** 指示器间距（dp） */
    private int[] indicatorMargins;
    /** 指示器非当前页资源 */
    private int[] indicatorRes;
    /** 指示器位置 */
    private int indicatorGravity;
    /** 是否显示指示器 */
    private boolean showIndicator;
    /** 网格，即行列数 */
    private int[] grid;
    /** 滑动有效距离 */
    private int swipePercent;

    private PageBuilder(Builder builder) {
        this.indicatorSize = builder.indicatorSize;
        this.indicatorMargins = builder.indicatorMargins;
        this.indicatorRes = builder.indicatorRes;
        this.indicatorGravity = builder.indicatorGravity;
        this.grid = builder.grid;
        this.swipePercent = builder.swipePercent;
        this.showIndicator = builder.showIndicator;
    }

    public int getIndicatorSize() {
        return indicatorSize;
    }

    public int[] getIndicatorMargins() {
        return indicatorMargins;
    }

    public int[] getIndicatorRes() {
        return indicatorRes;
    }

    public int getIndicatorGravity() {
        return indicatorGravity;
    }

    public int[] getGrid() {
        return grid;
    }

    public int getSwipePercent() {
        return swipePercent;
    }

    public boolean isShowIndicator() {
        return showIndicator;
    }

    public static class Builder {

        /** 指示器的大小（dp） */
        private int indicatorSize = 10;
        /** 指示器间距（dp） */
        private int[] indicatorMargins = {4,4, 5, 4};
        /** 指示器默认资源 */
        private int[] indicatorRes = {android.R.drawable.presence_invisible, android.R.drawable.presence_online};
        /** 指示器位置 */
        private int indicatorGravity = Gravity.CENTER;
        /** 网格，即行列数 */
        private int[] grid = {3, 4};
        /** 翻页百分比 */
        private int swipePercent = 50;
        /** 是否显示指示器 */
        private boolean showIndicator = true;

        /**
         * 配置翻页指示器大小
         *
         * @param size 翻页指示器大小
         * @return 构建器
         */
        public Builder setIndicatorSize(int size) {
            indicatorSize = size;
            return this;
        }

        /**
         * 配置翻页指示器间距
         *
         * @param left   左间距
         * @param top    上间距
         * @param right  有间距
         * @param bottom 下间距
         * @return 构建器
         */
        public Builder setIndicatorMargins(int left, int top, int right, int bottom) {
            indicatorMargins[0] = left;
            indicatorMargins[1] = top;
            indicatorMargins[2] = right;
            indicatorMargins[3] = bottom;
            return this;
        }

        /**
         * 配置翻页指示器资源
         *
         * @param normal 非当前页指示器资源
         * @param focus  当前页指示器资源
         * @return 构建器
         */
        public Builder setIndicatorRes(int normal, int focus) {
            indicatorRes[0] = normal;
            indicatorRes[1] = focus;
            return this;
        }

        /**
         * 配置翻页指示器位置
         *
         * @param gravity 翻页指示器位置
         * @return 构建器
         */
        public Builder setIndicatorGravity(int gravity) {
            indicatorGravity = gravity;
            return this;
        }

        /**
         * 设置网格，几行几列
         *
         * @param row    网格行数
         * @param column 网格列数
         * @return 构建器
         */
        public Builder setGrid(int row, int column) {
            grid[0] = row;
            grid[1] = column;
            return this;
        }

        /**
         * 设置翻页滑动距离百分比，达到有效距离后翻页（1-100）
         *
         * @param swipePercent 翻页的有效百分比
         * @return 构建器
         */
        public Builder setSwipePercent(int swipePercent) {
            this.swipePercent = swipePercent;
            return this;
        }

        /**
         * 设置是否使用分页指示器
         *
         * @param showIndicator true为使用，false为不使用
         * @return 构建器
         */
        public Builder setShowIndicator(boolean showIndicator) {
            this.showIndicator = showIndicator;
            return this;
        }

        public PageBuilder build() {
            return new PageBuilder(this);
        }
    }
}
