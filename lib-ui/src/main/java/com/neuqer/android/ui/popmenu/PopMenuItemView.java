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

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neuqer.android.ui.R;

/**
 * PopMenu Item View
 *
 * @author techflowing
 * @since 2018/10/21 11:20 PM
 */
public class PopMenuItemView extends LinearLayout {

    private Context mContext;
    private ImageView mItemImg;
    private TextView mItemTitle;
    private PopMenuItem mItem;

    public PopMenuItemView(Context context) {
        this(context, null);
    }

    public PopMenuItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopMenuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.pop_menu_item_view, this);
        mItemImg = findViewById(R.id.item_img);
        mItemTitle = findViewById(R.id.item_title);
    }

    public void setMenuItem(PopMenuItem item) {
        if (item == null) {
            return;
        }
        mItem = item;
        mItemImg.setImageDrawable(item.getDrawable());
        mItemTitle.setText(item.getTitle());
    }

    public PopMenuItem getItem() {
        return mItem;
    }
}
