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

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.neuqer.android.ui.R;
import com.neuqer.android.util.DeviceInfo;
import com.neuqer.android.util.UI;

/**
 * PopMenu
 *
 * @author techflowing
 * @since 2018/10/21 11:31 PM
 */
public class PopMenu {

    private Activity mContext;
    private int mColumn;
    private List<PopMenuItem> mPopMenuList;
    private RelativeLayout mMenuPanel;
    private GridLayout mGridLayout;
    private PopMenuItemClickListener mItemClickListener;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    private int mMarginToBottom;
    private boolean isShowing = false;

    private PopMenu(Builder builder) {
        mContext = builder.mContext;
        mColumn = builder.mColumn;
        mPopMenuList = builder.mPopMenuList;
        mItemClickListener = builder.mItemClickListener;
        mHorizontalPadding = builder.mHorizontalPadding;
        mVerticalPadding = builder.mVerticalPadding;
        mMarginToBottom = builder.mMarginToBottom;
        mScreenWidth = DeviceInfo.getScreenWidth(mContext);
        mScreenHeight = DeviceInfo.getScreenHeight(mContext);
    }

    /**
     * 显示菜单面板
     */
    public void show() {
        buildAnimateGridLayout();
        if (mMenuPanel.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) mMenuPanel.getParent();
            viewGroup.removeView(mMenuPanel);
        }

        ViewGroup decorView = (ViewGroup) mContext.getWindow().getDecorView();
        decorView.addView(mMenuPanel);

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mMenuPanel.getLayoutParams();
        lp.setMargins(0, 0, 0, DeviceInfo.getNavigationBarHeight(mContext));
        mMenuPanel.setLayoutParams(lp);

        // 执行显示动画
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.pop_menu_in);
        mMenuPanel.startAnimation(animation);
        showMenuItem();
        isShowing = true;
    }

    /**
     * 当前面板是否在显示
     */
    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 隐藏面板
     */
    public void dismiss() {
        ViewGroup decorView = (ViewGroup) mContext.getWindow().getDecorView();
        decorView.removeView(mMenuPanel);
        isShowing = false;
    }

    /**
     * 点击菜单时候，隐藏面板
     *
     * @param itemView 点击的item
     */
    private void dismiss(PopMenuItemView itemView) {
        if (itemView != null) {
            Animation scaleOut = AnimationUtils.loadAnimation(mContext, R.anim.pop_menu_item_scale_out);
            itemView.startAnimation(scaleOut);
        }
        Animation scaleOutSmall = AnimationUtils.loadAnimation(mContext, R.anim.pop_menu_item_scale_out_small);
        scaleOutSmall.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        int childCount = mGridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mGridLayout.getChildAt(i);
            if (view != itemView) {
                view.startAnimation(scaleOutSmall);
            }
        }
    }

    /**
     * 初始化，动态填充元素
     */
    private void buildAnimateGridLayout() {
        mMenuPanel = new RelativeLayout(mContext);
        mMenuPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(null);
            }
        });
        mGridLayout = new GridLayout(mContext);
        mGridLayout.setColumnCount(mColumn);
        mGridLayout.setPadding(0, 0, 0, UI.dp2px(mContext, mMarginToBottom));
        Drawable drawable = mContext.getResources().getDrawable(R.color.white);
        drawable.setAlpha(250);
        mGridLayout.setBackground(drawable);

        int hPadding = UI.dp2px(mContext, mHorizontalPadding);
        int vPadding = UI.dp2px(mContext, mVerticalPadding);
        int itemWidth = (mScreenWidth - (mColumn + 1) * hPadding) / mColumn;
        int itemHeight = itemWidth;

        int rowCount = mPopMenuList.size() % mColumn == 0 ? mPopMenuList.size() / mColumn :
                mPopMenuList.size() / mColumn + 1;
        int topMargin = mScreenHeight - UI.dp2px(mContext, mMarginToBottom)
                - (itemHeight + vPadding) * rowCount + vPadding;

        for (int i = 0; i < mPopMenuList.size(); i++) {
            final PopMenuItemView itemView = new PopMenuItemView(mContext);
            final PopMenuItem menuItem = mPopMenuList.get(i);
            itemView.setMenuItem(menuItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(menuItem);
                    }
                    dismiss(itemView);
                }
            });

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = itemWidth;
            lp.height = itemHeight;
            lp.leftMargin = hPadding;
            if (i / mColumn == 0) {
                lp.topMargin = topMargin;
            } else {
                lp.topMargin = vPadding;
            }
            mGridLayout.addView(itemView, lp);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mMenuPanel.addView(mGridLayout, params);
    }

    /**
     * 显示菜单项
     */
    private void showMenuItem() {
        if (mGridLayout == null) {
            return;
        }
        Animation scaleIn = AnimationUtils.loadAnimation(mContext, R.anim.pop_menu_item_scale_in);
        int childCount = mGridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = mGridLayout.getChildAt(i);
            view.startAnimation(scaleIn);
        }
    }


    public static final class Builder {
        /** item水平之间的间距 */
        private static final int DEFAULT_HORIZONTAL_PADDING = 40;
        /** item竖直之间的间距 */
        private static final int DEFAULT_VERTICAL_PADDING = 15;
        /** 默认Item列数 */
        private static final int DEFAULT_COLUMN = 3;
        /** 菜单距离底部距离 */
        private static final int DEFAULT_MARGIN_TO_BOTTOM = 32;
        private Activity mContext;
        private int mColumn = DEFAULT_COLUMN;
        private List<PopMenuItem> mPopMenuList;
        private PopMenuItemClickListener mItemClickListener;
        private int mHorizontalPadding = DEFAULT_HORIZONTAL_PADDING;
        private int mVerticalPadding = DEFAULT_VERTICAL_PADDING;
        private int mMarginToBottom = DEFAULT_MARGIN_TO_BOTTOM;

        public Builder(Activity context) {
            mContext = context;
            mPopMenuList = new ArrayList<>();
        }

        public Builder setColumn(int column) {
            mColumn = column;
            return this;
        }

        public Builder setItemClickListener(PopMenuItemClickListener itemClickListener) {
            mItemClickListener = itemClickListener;
            return this;
        }

        public Builder setHorizontalPadding(int horizontalPadding) {
            mHorizontalPadding = horizontalPadding;
            return this;
        }

        public Builder setVerticalPadding(int verticalPadding) {
            mVerticalPadding = verticalPadding;
            return this;
        }

        public Builder setMarginToBottom(int marginToBottom) {
            mMarginToBottom = marginToBottom;
            return this;
        }

        public Builder addMenuItem(PopMenuItem popMenuItem) {
            mPopMenuList.add(popMenuItem);
            return this;
        }

        public PopMenu build() {
            return new PopMenu(this);
        }
    }
}
