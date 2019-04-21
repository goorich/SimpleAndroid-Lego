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
 * Origin: https://github.com/Dsiner/Common
 */

package com.neuqer.android.ui.loading;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

import com.neuqer.android.ui.R;

/**
 * 自定义LoadingView，自绘制，支持多种样式
 *
 * @author techflowing
 * @since 2018/9/24 上午1:52
 */
public class LoadingView extends View {
    /** 默认分片数 */
    private static final int DEFAULT_COUNT = 12;
    /** 类型：菊花 */
    private static final int TYPE_DAISY = 0;
    /** 类型：园点 */
    private static final int TYPE_DOT = 1;
    /** 默认类型 */
    private static final int DEFAULT_TYPE = TYPE_DAISY;
    /** 一圈花费的时间 */
    private static final int DEFAULT_DURATION = 1000;
    /** 最小透明度 */
    private static final int MIN_ALPHA = 50;
    /** 分片占高度的比例 */
    private static final float HEIGHT_RATE = 1f / 2;
    /** 宽度和高度的比例 */
    private static final float WIDTH_RATE = 1f / 3;
    /** 宽度 */
    private float mWidth;
    /** 高度 */
    private float mHeight;
    /** 山下文 */
    private Context mContext;
    /** 类型 */
    private int mType;
    /** {@link Paint} */
    private Paint mPaint;
    /** 分片数 */
    private int mCount;
    /** {@link RectF} */
    private RectF mTempRct;
    /** 最小透明度 */
    private int mMinAlpha;
    /** 画板宽 */
    private float mRectWidth;
    /** 绘制的次数 */
    private int mOnDrawnTime;
    /** 循环绘制Handler */
    private Handler mHandler;
    /** Runnable */
    private Task mRunnable;
    /** 转一圈时间 */
    private long mDuration;
    /** 宽度和高度的比例 */
    private float mWidthRate;
    /** 分片占高度的比例 */
    private float mHeightRate;
    /** 是否是第一次绘制 */
    private boolean mIsFirst;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化方法
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray custom = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        mCount = custom.getInt(R.styleable.LoadingView_count, DEFAULT_COUNT);
        mType = custom.getInt(R.styleable.LoadingView_type, DEFAULT_TYPE);
        mDuration = custom.getInt(R.styleable.LoadingView_duration, DEFAULT_DURATION);
        mIsFirst = true;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(custom.getColor(R.styleable.LoadingView_color,
                ContextCompat.getColor(context, R.color.primary)));
        mMinAlpha = MIN_ALPHA;
        mWidthRate = WIDTH_RATE;
        mHeightRate = HEIGHT_RATE;
        mHandler = new Handler();
        mRunnable = new Task(this);
        custom.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0 || mHeight == 0 || mTempRct == null) {
            return;
        }
        canvas.translate(mWidth / 2, mHeight / 2);
        mOnDrawnTime++;
        mOnDrawnTime %= mCount;
        int alpha;
        for (int i = 0; i < mCount; i++) {
            canvas.rotate(360f / mCount);
            alpha = (i - mOnDrawnTime + mCount) % mCount;
            alpha = (int) (((alpha) * (255f - mMinAlpha) / mCount + mMinAlpha));
            mPaint.setAlpha(alpha);
            switch (mType) {
                case TYPE_DAISY:
                    canvas.drawRoundRect(mTempRct, mRectWidth / 2, mRectWidth / 2, mPaint);
                    break;
                case TYPE_DOT:
                    canvas.drawCircle((mTempRct.left + mTempRct.right) / 2, (mTempRct.top + mTempRct.bottom) / 2,
                            mRectWidth * 2 / 3, mPaint);
                    break;
                default:
                    break;
            }
        }
        if (mIsFirst) {
            mIsFirst = false;
            mHandler.postDelayed(mRunnable, mDuration / mCount);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        refreshField();
    }

    private void refreshField() {
        float h = mWidth > mHeight ? mHeight : mWidth;
        float rectHeight = h * mHeightRate / 2;
        float radius = h * (1 - mHeightRate / 2) / 2;
        mRectWidth = rectHeight * mWidthRate;
        mTempRct = new RectF(-mRectWidth / 2, -(radius + rectHeight / 2), mRectWidth / 2, -(radius - rectHeight / 2));
    }

    @Override
    public void setVisibility(int visibility) {
        switch (visibility) {
            case VISIBLE:
                restart();
                break;
            case GONE:
            case INVISIBLE:
                stop();
                break;
        }
        super.setVisibility(visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        if (!mIsFirst) {
            restart();
        }
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    /**
     * Restart
     */
    public void restart() {
        stop();
        mHandler.post(mRunnable);
    }

    /**
     * Stop
     */
    public void stop() {
        mIsFirst = false;
        mHandler.removeCallbacks(mRunnable);
    }

    /**
     * 自定义Runnable
     */
    private static class Task implements Runnable {

        /** 弱引用，避免内存泄漏 */
        WeakReference<LoadingView> weakRef;

        Task(LoadingView view) {
            this.weakRef = new WeakReference<>(view);
        }

        @Override
        public void run() {
            if (isFinished()) {
                return;
            }
            LoadingView theView = weakRef.get();
            theView.invalidate();
            theView.mHandler.postDelayed(theView.mRunnable, theView.mDuration / theView.mCount);
        }

        private boolean isFinished() {
            return weakRef == null || weakRef.get() == null
                    || weakRef.get().mContext == null
                    || weakRef.get().mContext instanceof Activity && ((Activity) weakRef.get().mContext).isFinishing();
        }
    }
}
