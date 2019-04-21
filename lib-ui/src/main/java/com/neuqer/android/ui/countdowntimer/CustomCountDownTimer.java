package com.neuqer.android.ui.countdowntimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.neuqer.android.ui.R;

/**
 * 自定义倒计时控件
 *
 * @author chengkun
 * @since 2018/11/21 20:44
 */
@SuppressLint("AppCompatCustomView")
public class CustomCountDownTimer extends TextView {

    private String mInitialText = "发送验证码";
    private String mAfterText = "再次发送验证码";
    private long mInterval = 1000;
    private long mTotal = 60000;
    private static long sCurrentMills = 0;
    private static boolean isFirstIn = true;
    NewCountDownTimer timer;

    public CustomCountDownTimer(Context context) {
        this(context, null);
    }

    public CustomCountDownTimer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCountDownTimer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_countdown_timer);
        mInitialText = typedArray.getString(R.styleable.custom_countdown_timer_initial_text);
        mAfterText = typedArray.getString(R.styleable.custom_countdown_timer_after_text);
        mInterval = typedArray.getInt(R.styleable.custom_countdown_timer_interval, 1000);
        mTotal = typedArray.getInt(R.styleable.custom_countdown_timer_total_time, 60000);
        typedArray.recycle();

        setText(mInitialText);
    }

    /**
     * 设置总共时间
     */
    public CustomCountDownTimer setTotal(long total) {
        this.mTotal = total;
        return this;
    }

    /**
     * 设置单位时间
     */
    public CustomCountDownTimer setInterval(long interval) {
        this.mInterval = interval;
        return this;
    }

    /**
     * 设置最初显示文字
     */
    public CustomCountDownTimer setInitialText(String initialText) {
        this.mInitialText = initialText;
        setText(mInitialText);
        return this;
    }

    /**
     * 设置完成后文字
     */
    public CustomCountDownTimer setAfterText(String afterText) {
        this.mAfterText = afterText;
        return this;
    }

    /**
     * 开启计时器
     */
    public void initTimer(boolean onClick) {
        if (!isFirstIn && sCurrentMills + mTotal > System.currentTimeMillis()) {
            startTimer(sCurrentMills + mTotal - System.currentTimeMillis());
        } else if (onClick) {
            sCurrentMills = System.currentTimeMillis();
            startTimer(mTotal);
        }
    }

    /**
     * 开始计时
     *
     * @param countDownTime 计时器一开始显示时间
     */
    private void startTimer(long countDownTime) {

        isFirstIn = false;
        timer = new NewCountDownTimer(countDownTime, mInterval);
        timer.start();
    }

    public void cancle() {
        timer.cancel();
    }

    class NewCountDownTimer extends CountDownTimer {

        public NewCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setEnabled(false);
            String time = millisUntilFinished / 1000 + " s";
            setText(time);
        }

        @Override
        public void onFinish() {
            setEnabled(true);
            setText(mAfterText);
        }
    }
}
