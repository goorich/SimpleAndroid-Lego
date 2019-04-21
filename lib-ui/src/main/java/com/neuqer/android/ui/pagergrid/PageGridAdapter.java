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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向网格布局适配器
 *
 * @author techflowing
 * @since 2018/10/18 10:56 PM
 */
public abstract class PageGridAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /** 转换后的数据 */
    private List<T> mData;
    /** 元素的宽度 */
    private int mItemWidth;
    /** 行列数 */
    private int mRow, mColumn;
    /** Item点击事件 */
    private OnItemClickListener<T> mOnItemClickListener;


    public PageGridAdapter(PageBuilder pageBuilder) {
        mRow = pageBuilder.getGrid()[0];
        mColumn = pageBuilder.getGrid()[1];
    }

    /**
     * 设置数据源
     *
     * @param data 数据源
     */
    public void setData(List<T> data) {
        mData = new ArrayList<>();
        mData.addAll(convert(data));
        notifyDataSetChanged();
    }

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * 获取指定Item数据
     *
     * @param position 位置
     */
    public T getItemData(int position) {
        if (mData == null || position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    /**
     * 设置Item点击事件
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 横向布局下，默认的排列顺序是从上到下从左到右。这里需要转换成从左到右从上到下。
     * <p>
     * 例如2行4列的原始数据：
     * 1、3、5、7
     * 2、4、6、8
     * <p>
     * 转换后的数据
     * 1、2、3、4
     * 5、6、7、8
     *
     * @param origin 原始数据
     * @return 转换排列后的数据
     */
    private List<T> convert(List<T> origin) {
        if (origin == null) {
            return new ArrayList<>();
        }
        List<T> dest = new ArrayList<>();

        int pageSize = mRow * mColumn;
        int pageCount = (int) Math.ceil(origin.size() / (double) pageSize);
        for (int p = 0; p < pageCount; p++) {
            for (int c = 0; c < mColumn; c++) {
                for (int r = 0; r < mRow; r++) {
                    int index = c + r * mColumn + p * pageSize;
                    if (index < origin.size()) {
                        dest.add(origin.get(index));
                    } else {
                        dest.add(null); // 填充虚位做交换，保证数据满屏，后面过滤处理
                    }
                }
            }
        }
        return dest;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItemWidth <= 0) {
            // 计算Item的宽度
            mItemWidth = parent.getMeasuredWidth() / mColumn;
        }
        VH holder = getItemViewHolder(parent, viewType);
        holder.itemView.measure(0, 0);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = mItemWidth;
        layoutParams.height = holder.itemView.getMeasuredHeight();
        holder.itemView.setLayoutParams(layoutParams);
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (position < mData.size() && mData.get(position) != null) {
            // 将要显示的item交给回调处理
            holder.itemView.setVisibility(View.VISIBLE);
            bindItemViewHolder(holder, position);
        } else {
            holder.itemView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, mData.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    protected abstract VH getItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindItemViewHolder(VH holder, int position);


    public interface OnItemClickListener<T> {
        void onClick(View v, T model);
    }
}
