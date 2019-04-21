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

package com.neuqer.android.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView Adapter封装
 *
 * @author techflowing
 * @since 2018/10/17 12:02 AM
 */
public abstract class SimpleAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<T> mData;

    public SimpleAdapter(Context context) {
        mContext = context;
    }

    public void updateData(List<T> data) {
        this.mData = data;
    }

    protected T getItemModel(int position) {
        if (mData == null || position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}
