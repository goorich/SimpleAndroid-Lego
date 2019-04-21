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

package com.neuqer.android.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neuqer.android.ui.R;

/**
 * 单行Item，一般用于我的页面item¬
 *
 * @author techflowing
 * @since 2018/10/3 10:56 PM
 */
public class RowLayout extends RelativeLayout {

    private ImageView mIconImg;
    private ImageView mMoreImg;
    private TextView mNumberTxt;
    private TextView mContentTxt;
    private TextView mHintTxt;
    private SwitchCompat mSwitch;
    private CompoundButton.OnCheckedChangeListener mSwitchChangeListener;
    private Drawable mIcon;
    private Drawable mMoreIcon;
    private final String mContent;
    private final String mHint;
    private int mVisibilitySwitch;
    private int mVisibilityMore;

    public RowLayout(Context context) {
        this(context, null);
    }

    public RowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RowLayout);
        mContent = typedArray.getString(R.styleable.RowLayout_row_text);
        mHint = typedArray.getString(R.styleable.RowLayout_row_hint);
        mIcon = typedArray.getDrawable(R.styleable.RowLayout_row_icon);
        mMoreIcon = typedArray.getDrawable(R.styleable.RowLayout_row_more_icon);
        mVisibilitySwitch = typedArray.getInteger(R.styleable.RowLayout_row_switch_visibility, View.GONE);
        mVisibilityMore = typedArray.getInteger(R.styleable.RowLayout_row_more_visibility, View.GONE);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.row_layout, this);
        mIconImg = root.findViewById(R.id.img_row_layout_icon);
        mNumberTxt = root.findViewById(R.id.txt_row_layout_number);
        mContentTxt = root.findViewById(R.id.txt_row_layout_content);
        mHintTxt = root.findViewById(R.id.txt_row_layout_hint);
        mSwitch = root.findViewById(R.id.switch_row_layout);
        mMoreImg = root.findViewById(R.id.img_row_layout_more);
        if (mIcon != null) {
            mIconImg.setImageDrawable(mIcon);
            mIconImg.setVisibility(VISIBLE);
        } else {
            mIconImg.setVisibility(GONE);
        }
        if (mMoreIcon != null) {
            mMoreImg.setImageDrawable(mMoreIcon);
        }
        mMoreImg.setVisibility(mVisibilityMore);
        mContentTxt.setText(mContent != null ? mContent : "");
        mHintTxt.setText(mHint != null && mVisibilityMore == VISIBLE ? mHint : "");
        mSwitch.setVisibility(mVisibilitySwitch);
        if (mSwitchChangeListener != null) {
            mSwitch.setOnCheckedChangeListener(mSwitchChangeListener);
        }
    }

    public void setText(CharSequence text) {
        mContentTxt.setText(text);
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
        mIconImg.setImageDrawable(mIcon);
        mIconImg.setVisibility(View.VISIBLE);
    }

    public void setHint(CharSequence text, int visibility) {
        if (mVisibilityMore != View.VISIBLE) {
            return;
        }
        mHintTxt.setText(text);
        mHintTxt.setVisibility(visibility);
    }

    public void setNumber(CharSequence text, int visibility) {
        if (mVisibilityMore != View.VISIBLE) {
            return;
        }
        mNumberTxt.setText(text);
        mNumberTxt.setVisibility(visibility);
    }

    public void setMoreImgVisibility(int visibility) {
        mMoreImg.setVisibility(visibility);
    }

    public void setSwitchStatus(boolean checked) {
        mSwitch.setChecked(checked);
    }

    public boolean isChecked() {
        return mSwitch.isChecked();
    }

    public void setSwitchChangeListener(CompoundButton.OnCheckedChangeListener switchChangeListener) {
        mSwitchChangeListener = switchChangeListener;
    }
}

