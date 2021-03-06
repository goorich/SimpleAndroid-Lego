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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 横向滑动网格布局
 *
 * @author techflowing
 * @since 2018/10/18 10:56 PM
 */
public class PageGridView extends RecyclerView {

    /** Adapter */
    private PageGridAdapter adapter;
    /** 总页数 */
    private int totalPage;
    /** 翻页指示器 */
    private PageIndicatorView pageIndicatorView;
    /** 滑动翻页的有效距离 */
    private int validDistance;
    /** 滑动的距离 */
    private float swipeDistance = 0;
    /** 当前页 */
    private int currentPage = 1;
    /** X轴当前的位置 */
    private float scrollX = 0;
    /** 行列数 */
    private int row, column;
    /** 滑动百分比 */
    private int swipePercent;
    /** 滚动状态 */
    private int scrollStatus = 0;

    public PageGridView(Context context, int[] grid, int swipePercent) {
        super(context);
        this.row = grid[0];
        this.column = grid[1];
        this.swipePercent = swipePercent;
        setOverScrollMode(OVER_SCROLL_NEVER);// 屏蔽顶端的阴影效果
        setLayoutManager(new GridLayoutManager(getContext(), row,
                GridLayoutManager.HORIZONTAL, false) {
            @Override
            public int scrollHorizontallyBy(int dx, Recycler recycler, State state) {
                //只有一页的时候不能滑动
                if (totalPage > 1) {
                    return super.scrollHorizontallyBy(dx, recycler, state);
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     * 设置指示器
     *
     * @param pageIndicatorView 指示器布局
     */
    public void setIndicator(PageIndicatorView pageIndicatorView) {
        this.pageIndicatorView = pageIndicatorView;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (swipePercent < 1) {
            swipePercent = 1;
        } else if (swipePercent > 100) {
            swipePercent = 100;
        }
        validDistance = getMeasuredWidth() * swipePercent / 100;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        this.adapter = (PageGridAdapter) adapter;
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        if (adapter != null) {
            update();
        }
    }

    /**
     * 更新页码指示器和相关数据
     */
    public void update() {
        // 计算总页数
        int count = ((int) Math.ceil(adapter.getData().size() / (double) (row * column)));
        if (count != totalPage) {
            pageIndicatorView.initIndicator(count);
            // 页码减少且当前页为最后一页
            if (count < totalPage && currentPage == totalPage) {
                currentPage = count;
                // 执行滚动
                smoothScrollBy(-getWidth(), 0);
            }
            pageIndicatorView.update(currentPage - 1);
            totalPage = count;
        }

        // 当页面为1时不显示指示器
        if (totalPage > 1) {
            pageIndicatorView.setVisibility(VISIBLE);
        } else {
            pageIndicatorView.setVisibility(GONE);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case SCROLL_STATE_SETTLING:
                scrollStatus = SCROLL_STATE_SETTLING;
                break;
            case SCROLL_STATE_DRAGGING:
                scrollStatus = SCROLL_STATE_DRAGGING;
                break;
            case SCROLL_STATE_IDLE:
                if (swipeDistance == 0) {
                    break;
                }
                scrollStatus = SCROLL_STATE_IDLE;
                if (swipeDistance < 0) { // 上页
                    currentPage = (int) Math.ceil(scrollX / getWidth());
                    if (currentPage * getWidth() - scrollX < validDistance) {
                        currentPage += 1;
                    }
                } else { // 下页
                    currentPage = (int) Math.ceil(scrollX / getWidth()) + 1;
                    if (currentPage <= totalPage) {
                        if (scrollX - (currentPage - 2) * getWidth() < validDistance) {
                            // 如果这一页滑出距离不足，则定位到前一页
                            currentPage -= 1;
                        }
                    } else {
                        currentPage = totalPage;
                    }
                }
                // 执行自动滚动
                smoothScrollBy((int) ((currentPage - 1) * getWidth() - scrollX), 0);
                // 更新翻页指示器
                pageIndicatorView.update(currentPage - 1);
                swipeDistance = 0;
                break;
        }
        super.onScrollStateChanged(state);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        scrollX += dx;
        if (scrollStatus == SCROLL_STATE_DRAGGING) {
            swipeDistance += dx;
        }
        super.onScrolled(dx, dy);
    }
}
