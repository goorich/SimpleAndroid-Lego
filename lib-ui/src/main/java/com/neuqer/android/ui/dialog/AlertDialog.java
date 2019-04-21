package com.neuqer.android.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.neuqer.android.ui.R;

/**
 * AlertDialog
 *
 * @author techflowing
 * @since 2018/9/24 下午10:08
 */
public class AlertDialog extends AbsDialog {

    private TextView mTitle;
    private TextView mMessage;
    private Button mNegativeBtn;
    private Button mNeutralBtn;
    private Button mPositiveBtn;

    public AlertDialog(@NonNull Context context) {
        super(context, R.style.dialog, Gravity.CENTER,
                context.getResources().getDimensionPixelOffset(R.dimen.dialog_width),
                WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_alert_container;
    }

    @Override
    protected void init(View rootView) {
        mTitle = rootView.findViewById(R.id.txt_title);
        mMessage = rootView.findViewById(R.id.txt_message);
        mNegativeBtn = rootView.findViewById(R.id.btn_negative);
        mPositiveBtn = rootView.findViewById(R.id.btn_positive);
        mNeutralBtn = rootView.findViewById(R.id.btn_neutral);
    }

    public AlertDialog setTitle(String text) {
        if (mTitle == null) {
            return this;
        }
        if (TextUtils.isEmpty(text)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(text);
        }
        return this;
    }

    public AlertDialog setMessage(String text) {
        if (mMessage == null) {
            return this;
        }
        if (TextUtils.isEmpty(text)) {
            mMessage.setVisibility(View.GONE);
        } else {
            mMessage.setVisibility(View.VISIBLE);
            mMessage.setText(text);
        }
        return this;
    }

    public AlertDialog setNegativeButton(String text, final View.OnClickListener listener) {
        if (mNegativeBtn == null) {
            return this;
        }
        if (TextUtils.isEmpty(text)) {
            mNegativeBtn.setVisibility(View.GONE);
        } else {
            mNegativeBtn.setVisibility(View.VISIBLE);
            mNegativeBtn.setText(text);
            mNegativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (listener != null) {
                        listener.onClick(v);
                    }
                }
            });
        }
        return this;
    }

    public AlertDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if (mPositiveBtn == null) {
            return this;
        }
        mPositiveBtn.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(text)) {
            text = getContext().getString(R.string.dialog_positive_text);
        }
        mPositiveBtn.setText(text);
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    public AlertDialog setNeutralButton(String text, final View.OnClickListener listener) {
        if (mNeutralBtn == null) {
            return this;
        }
        if (TextUtils.isEmpty(text)) {
            mNeutralBtn.setVisibility(View.GONE);
        } else {
            mNeutralBtn.setVisibility(View.VISIBLE);
            mNeutralBtn.setText(text);
            mNeutralBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (listener != null) {
                        listener.onClick(v);
                    }
                }
            });
        }
        return this;
    }

}
