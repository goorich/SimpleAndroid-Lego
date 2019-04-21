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
 * There are modifications to the origin source code.
 */

package com.neuqer.android.ui.tab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neuqer.android.ui.R;


/**
 * 无限Tab，联动ViewPager
 *
 * @author techflowing
 * @since 2018/10/3 3:47 PM
 */
public class PagerSlidingTab extends HorizontalScrollView {

    private static final int DEF_VALUE_TAB_TEXT_ALPHA = 150;
    private static final int[] ANDROID_ATTRS = new int[]{
            android.R.attr.textColorPrimary,
            android.R.attr.padding,
            android.R.attr.paddingLeft,
            android.R.attr.paddingRight,
    };
    private static final int TEXT_COLOR_PRIMARY = 0;
    private static final int PADDING_INDEX = 1;
    private static final int PADDING_LEFT_INDEX = 2;
    private static final int PADDING_RIGHT_INDEX = 3;

    private LinearLayout mTabsContainer;

    private final PagerAdapterObserver mAdapterObserver = new PagerAdapterObserver();
    private final PageListener mPageListener = new PageListener();
    private OnTabReselectedListener mTabReselectedListener = null;
    public OnPageChangeListener mDelegatePageListener;
    private ViewPager mPager;

    private int mTabCount;

    private int mCurrentPosition = 0;
    private float mCurrentPositionOffset = 0f;

    private Paint mRectPaint;
    private Paint mDividerPaint;

    private int mIndicatorColor;
    private int mIndicatorHeight = 2;

    private int mUnderlineHeight = 0;
    private int mUnderlineColor;

    private int mDividerWidth = 0;
    private int mDividerPadding = 0;
    private int mDividerColor;

    private int mTabPadding = 12;
    private int mTabTextSize = 14;
    private ColorStateList mTabTextColor = null;

    private int mPaddingLeft = 0;
    private int mPaddingRight = 0;

    private boolean isExpandTabs = false;
    private boolean isCustomTabs;
    private boolean isPaddingMiddle = false;
    private boolean isTabTextAllCaps = true;

    private Typeface mTabTextTypeface = null;
    private int mTabTextTypefaceStyle = Typeface.BOLD;

    private int mScrollOffset;
    private int mLastScrollX = 0;
    private int mTabInterval = 0;

    private int mTabBackgroundResId = R.drawable.pst_tab_bg;

    public PagerSlidingTab(Context context) {
        this(context, null);
    }

    public PagerSlidingTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTab(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        setWillNotDraw(false);
        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mTabsContainer);

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Style.FILL);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mScrollOffset, dm);
        mIndicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorHeight, dm);
        mUnderlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mUnderlineHeight, dm);
        mDividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDividerPadding, dm);
        mTabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTabPadding, dm);
        mDividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDividerWidth, dm);
        mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTabTextSize, dm);

        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStrokeWidth(mDividerWidth);

        // get system attrs for container
        TypedArray a = context.obtainStyledAttributes(attrs, ANDROID_ATTRS);
        int textPrimaryColor = a.getColor(TEXT_COLOR_PRIMARY, ContextCompat.getColor(context, android.R.color.black));
        mUnderlineColor = textPrimaryColor;
        mDividerColor = textPrimaryColor;
        mIndicatorColor = textPrimaryColor;
        int padding = a.getDimensionPixelSize(PADDING_INDEX, 0);
        mPaddingLeft = padding > 0 ? padding : a.getDimensionPixelSize(PADDING_LEFT_INDEX, 0);
        mPaddingRight = padding > 0 ? padding : a.getDimensionPixelSize(PADDING_RIGHT_INDEX, 0);
        a.recycle();

        String tabTextTypefaceName = "sans-serif";
        // Use Roboto Medium as the default typeface from API 21 onwards
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabTextTypefaceName = "sans-serif-medium";
            mTabTextTypefaceStyle = Typeface.NORMAL;
        }

        // get custom attrs for tabs and container
        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTab);
        mIndicatorColor = a.getColor(R.styleable.PagerSlidingTab_pstIndicatorColor, mIndicatorColor);
        mIndicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstIndicatorHeight, mIndicatorHeight);
        mUnderlineColor = a.getColor(R.styleable.PagerSlidingTab_pstUnderlineColor, mUnderlineColor);
        mUnderlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstUnderlineHeight, mUnderlineHeight);
        mDividerColor = a.getColor(R.styleable.PagerSlidingTab_pstDividerColor, mDividerColor);
        mDividerWidth = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstDividerWidth, mDividerWidth);
        mDividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstDividerPadding, mDividerPadding);
        isExpandTabs = a.getBoolean(R.styleable.PagerSlidingTab_pstShouldExpand, isExpandTabs);
        mScrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstScrollOffset, mScrollOffset);
        isPaddingMiddle = a.getBoolean(R.styleable.PagerSlidingTab_pstPaddingMiddle, isPaddingMiddle);
        mTabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstTabPaddingLeftRight, mTabPadding);
        mTabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTab_pstTabBackground, mTabBackgroundResId);
        mTabTextSize = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstTabTextSize, mTabTextSize);
        mTabTextColor = a.hasValue(R.styleable.PagerSlidingTab_pstTabTextColor) ?
                a.getColorStateList(R.styleable.PagerSlidingTab_pstTabTextColor) : null;
        mTabInterval = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pstTabInterval, mTabInterval);
        mTabTextTypefaceStyle = a.getInt(R.styleable.PagerSlidingTab_pstTabTextStyle, mTabTextTypefaceStyle);
        isTabTextAllCaps = a.getBoolean(R.styleable.PagerSlidingTab_pstTabTextAllCaps, isTabTextAllCaps);
        int tabTextAlpha = a.getInt(R.styleable.PagerSlidingTab_pstTabTextAlpha, DEF_VALUE_TAB_TEXT_ALPHA);
        String fontFamily = a.getString(R.styleable.PagerSlidingTab_pstTabTextFontFamily);
        a.recycle();

        // Tab text color selector
        if (mTabTextColor == null) {
            mTabTextColor = createColorStateList(
                    textPrimaryColor,
                    textPrimaryColor,
                    Color.argb(tabTextAlpha,
                            Color.red(textPrimaryColor),
                            Color.green(textPrimaryColor),
                            Color.blue(textPrimaryColor)));
        }

        // Tab text typeface and style
        if (fontFamily != null) {
            tabTextTypefaceName = fontFamily;
        }
        mTabTextTypeface = Typeface.create(tabTextTypefaceName, mTabTextTypefaceStyle);

        // Bottom padding for the tabs container parent view to show indicator and underline
        setTabsContainerParentViewPadding();
    }

    private void setTabsContainerParentViewPadding() {
        int bottomMargin = mIndicatorHeight >= mUnderlineHeight ? mIndicatorHeight : mUnderlineHeight;
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), bottomMargin);
    }

    public void setViewPager(ViewPager pager) {
        this.mPager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        isCustomTabs = pager.getAdapter() instanceof CustomTabProvider;
        pager.addOnPageChangeListener(mPageListener);
        pager.getAdapter().registerDataSetObserver(mAdapterObserver);
        mAdapterObserver.setAttached(true);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        mTabCount = mPager.getAdapter().getCount();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            if (isCustomTabs) {
                tabView = ((CustomTabProvider) mPager.getAdapter()).getCustomTabView(this, i);
            } else {
                tabView = LayoutInflater.from(getContext()).inflate(R.layout.psts_tab, this, false);
            }

            CharSequence title = mPager.getAdapter().getPageTitle(i);
            addTab(i, title, tabView);
        }

        updateTabStyles();
    }

    private void addTab(final int position, CharSequence title, View tabView) {
        TextView textView = (TextView) tabView.findViewById(R.id.pst_tab_title);
        if (textView != null) {
            if (title != null) textView.setText(title);
        }

        tabView.setFocusable(true);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() != position) {
                    View tab = mTabsContainer.getChildAt(mPager.getCurrentItem());
                    unSelect(tab);
                    mPager.setCurrentItem(position);
                } else if (mTabReselectedListener != null) {
                    mTabReselectedListener.onTabReselected(position);
                }
            }
        });
        LinearLayout.LayoutParams mTabLayoutParams = isExpandTabs ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        if (position != 0 && mTabInterval > 0) {
            mTabLayoutParams.setMarginStart(mTabInterval);
        } else {
            mTabLayoutParams.setMarginStart(0);
        }
        mTabsContainer.addView(tabView, position, mTabLayoutParams);
    }

    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View v = mTabsContainer.getChildAt(i);
            v.setBackgroundResource(mTabBackgroundResId);
            v.setPadding(mTabPadding, v.getPaddingTop(), mTabPadding, v.getPaddingBottom());
            TextView tab_title = (TextView) v.findViewById(R.id.pst_tab_title);
            if (tab_title != null) {
                tab_title.setTextColor(mTabTextColor);
                tab_title.setTypeface(mTabTextTypeface, mTabTextTypefaceStyle);
                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
                // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                // pre-ICS-build
                if (isTabTextAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab_title.setAllCaps(true);
                    } else {
                        tab_title.setText(tab_title.getText().toString().toUpperCase());
                    }
                }
            }
        }
    }

    private void scrollToChild(int position, int offset) {
        if (mTabCount == 0) {
            return;
        }

        int newScrollX = mTabsContainer.getChildAt(position).getLeft() + offset;
        if (position > 0 || offset > 0) {
            // Half screen offset.
            // - Either tabs start at the middle of the view scrolling straight away
            // - Or tabs start at the begging (no padding) scrolling when indicator gets
            //   to the middle of the view width
            newScrollX -= mScrollOffset;
            Pair<Float, Float> lines = getIndicatorCoordinates();
            newScrollX += ((lines.second - lines.first) / 2);
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    public Pair<Float, Float> getIndicatorCoordinates() {
        // default: line below current tab
        View currentTab = mTabsContainer.getChildAt(mCurrentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();
        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (mCurrentPositionOffset > 0f && mCurrentPosition < mTabCount - 1) {
            View nextTab = mTabsContainer.getChildAt(mCurrentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
            lineLeft = (mCurrentPositionOffset * nextTabLeft + (1f - mCurrentPositionOffset) * lineLeft);
            lineRight = (mCurrentPositionOffset * nextTabRight + (1f - mCurrentPositionOffset) * lineRight);
        }

        return new Pair<>(lineLeft, lineRight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isPaddingMiddle && mTabsContainer.getChildCount() > 0) {
            View view = mTabsContainer.getChildAt(0);
            int halfWidthFirstTab = view.getMeasuredWidth() / 2;
            mPaddingLeft = mPaddingRight = getWidth() / 2 - halfWidthFirstTab;
        }

        if (isPaddingMiddle || mPaddingLeft > 0 || mPaddingRight > 0) {
            int width;
            if (isPaddingMiddle) {
                width = getWidth();
            } else {
                // Account for manually set padding for offsetting tab start and end positions.
                width = getWidth() - mPaddingLeft - mPaddingRight;
            }
            // Make sure tabContainer is bigger than the HorizontalScrollView to be able to scroll
            mTabsContainer.setMinimumWidth(width);

            // Clipping padding to false to see the tabs while we pass them swiping
            setClipToPadding(false);
        }

        setPadding(mPaddingLeft, getPaddingTop(), mPaddingRight, getPaddingBottom());
        if (mScrollOffset == 0) {
            mScrollOffset = getWidth() / 2 - mPaddingLeft;
        }

        if (mPager != null) {
            mCurrentPosition = mPager.getCurrentItem();
        }

        mCurrentPositionOffset = 0f;
        scrollToChild(mCurrentPosition, 0);
        updateSelection(mCurrentPosition);
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || mTabCount == 0) {
            return;
        }

        final int height = getHeight();
        // draw divider
        if (mDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mDividerWidth);
            mDividerPaint.setColor(mDividerColor);
            for (int i = 0; i < mTabCount - 1; i++) {
                View tab = mTabsContainer.getChildAt(i);
                canvas.drawLine(tab.getRight(), mDividerPadding, tab.getRight(),
                        height - mDividerPadding, mDividerPaint);
            }
        }

        // draw underline
        if (mUnderlineHeight > 0) {
            mRectPaint.setColor(mUnderlineColor);
            canvas.drawRect(mPaddingLeft, height - mUnderlineHeight, mTabsContainer.getWidth() + mPaddingRight,
                    height, mRectPaint);
        }

        // draw indicator line
        if (mIndicatorHeight > 0) {
            mRectPaint.setColor(mIndicatorColor);
            Pair<Float, Float> lines = getIndicatorCoordinates();
            canvas.drawRect(lines.first + mPaddingLeft, height - mIndicatorHeight, lines.second + mPaddingLeft,
                    height, mRectPaint);
        }
    }

    private void updateSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tv = mTabsContainer.getChildAt(i);
            final boolean selected = i == position;
            if (selected) {
                select(tv);
            } else {
                unSelect(tv);
            }
        }
    }

    private void unSelect(View tab) {
        if (tab != null) {
            TextView tab_title = (TextView) tab.findViewById(R.id.pst_tab_title);
            if (tab_title != null) {
                tab_title.setSelected(false);
            }
            if (isCustomTabs) {
                ((CustomTabProvider) mPager.getAdapter()).tabUnselected(tab);
            }
        }
    }

    private void select(View tab) {
        if (tab != null) {
            TextView tab_title = (TextView) tab.findViewById(R.id.pst_tab_title);
            if (tab_title != null) {
                tab_title.setSelected(true);
            }
            if (isCustomTabs) {
                ((CustomTabProvider) mPager.getAdapter()).tabSelected(tab);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPager != null) {
            if (!mAdapterObserver.isAttached()) {
                mPager.getAdapter().registerDataSetObserver(mAdapterObserver);
                mAdapterObserver.setAttached(true);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPager != null) {
            if (mAdapterObserver.isAttached()) {
                mPager.getAdapter().unregisterDataSetObserver(mAdapterObserver);
                mAdapterObserver.setAttached(false);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPosition = savedState.currentPosition;
        if (mCurrentPosition != 0 && mTabsContainer.getChildCount() > 0) {
            unSelect(mTabsContainer.getChildAt(0));
            select(mTabsContainer.getChildAt(mCurrentPosition));
        }
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mCurrentPosition;
        return savedState;
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mCurrentPosition = position;
            mCurrentPositionOffset = positionOffset;
            int offset = mTabCount > 0 ? (int) (positionOffset * mTabsContainer.getChildAt(position).getWidth()) : 0;
            scrollToChild(position, offset);
            invalidate();
            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(mPager.getCurrentItem(), 0);
            }
            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            updateSelection(position);

            // Select current item
            View currentTab = mTabsContainer.getChildAt(position);
            select(currentTab);
            // UnSelect prev item
            if (position > 0) {
                View prevTab = mTabsContainer.getChildAt(position - 1);
                unSelect(prevTab);
            }
            // UnSelect next item
            if (position < mPager.getAdapter().getCount() - 1) {
                View nextTab = mTabsContainer.getChildAt(position + 1);
                unSelect(nextTab);
            }

            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageSelected(position);
            }
        }

    }

    private class PagerAdapterObserver extends DataSetObserver {

        private boolean attached = false;

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        void setAttached(boolean attached) {
            this.attached = attached;
        }

        boolean isAttached() {
            return attached;
        }
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public int getIndicatorHeight() {
        return mIndicatorHeight;
    }

    public int getUnderlineColor() {
        return mUnderlineColor;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public int getDividerWidth() {
        return mDividerWidth;
    }

    public int getUnderlineHeight() {
        return mUnderlineHeight;
    }

    public int getDividerPadding() {
        return mDividerPadding;
    }

    public int getScrollOffset() {
        return mScrollOffset;
    }

    public boolean getShouldExpand() {
        return isExpandTabs;
    }

    public int getTextSize() {
        return mTabTextSize;
    }

    public boolean isTextAllCaps() {
        return isTabTextAllCaps;
    }

    public ColorStateList getTextColor() {
        return mTabTextColor;
    }

    public int getTabBackground() {
        return mTabBackgroundResId;
    }

    public int getTabPaddingLeftRight() {
        return mTabPadding;
    }

    public LinearLayout getTabsContainer() {
        return mTabsContainer;
    }

    public int getTabCount() {
        return mTabCount;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public float getCurrentPositionOffset() {
        return mCurrentPositionOffset;
    }

    public void setOnTabReselectedListener(OnTabReselectedListener tabReselectedListener) {
        this.mTabReselectedListener = tabReselectedListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mDelegatePageListener = listener;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.mIndicatorColor = ContextCompat.getColor(getContext(), resId);
        invalidate();
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.mIndicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.mUnderlineColor = ContextCompat.getColor(getContext(), resId);
        invalidate();
    }

    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.mDividerColor = ContextCompat.getColor(getContext(), resId);
        invalidate();
    }

    public void setDividerWidth(int dividerWidthPx) {
        this.mDividerWidth = dividerWidthPx;
        invalidate();
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.mUnderlineHeight = underlineHeightPx;
        invalidate();
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.mDividerPadding = dividerPaddingPx;
        invalidate();
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.mScrollOffset = scrollOffsetPx;
        invalidate();
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.isExpandTabs = shouldExpand;
        if (mPager != null) {
            requestLayout();
        }
    }

    public void setAllCaps(boolean textAllCaps) {
        this.isTabTextAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.mTabTextSize = textSizePx;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        setTextColor(ContextCompat.getColor(getContext(), resId));
    }

    public void setTextColor(int textColor) {
        setTextColor(createColorStateList(textColor));
    }

    public void setTextColorStateListResource(int resId) {
        setTextColor(ContextCompat.getColorStateList(getContext(), resId));
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mTabTextColor = colorStateList;
        updateTabStyles();
    }

    private ColorStateList createColorStateList(int color_state_default) {
        return new ColorStateList(
                new int[][]{new int[]{}},
                new int[]{color_state_default}
        );
    }

    private ColorStateList createColorStateList(int color_state_pressed, int color_state_selected, int color_state_default) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed}, // pressed
                        new int[]{android.R.attr.state_selected}, // enabled
                        new int[]{} // default
                },
                new int[]{
                        color_state_pressed,
                        color_state_selected,
                        color_state_default
                }
        );
    }

    public void setTypeface(Typeface typeface, int style) {
        this.mTabTextTypeface = typeface;
        this.mTabTextTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.mTabBackgroundResId = resId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.mTabPadding = paddingPx;
        updateTabStyles();
    }

    /**
     * 自定义Item实现
     */
    public interface CustomTabProvider {
        /**
         * 构造View
         */
        View getCustomTabView(ViewGroup parent, int position);

        /**
         * Tab被选择
         */
        void tabSelected(View tab);

        /***
         * Tab取消选择
         */
        void tabUnselected(View tab);
    }

    /**
     * Tab重复选择监听器（点击当前Tab）
     */
    public interface OnTabReselectedListener {
        /**
         * 重复选择
         *
         * @param position 位置
         */
        void onTabReselected(int position);
    }
}

