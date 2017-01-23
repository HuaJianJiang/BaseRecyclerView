/*
 * Copyright (c) 2017 HuaJian Jiang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.huajianjiang.baserecyclerview.widget;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements ViewHolderCallback {
    private static final String TAG = "BaseViewHolder";

    /**
     * itemView 或者 itemView 的子 view 交互事件监听器
     */
    private ViewEventWatcher mViewEventWatcher;
    /**
     * ItemView 的 childView 缓存
     * 便于根据 id 查找对应的 View
     * 如果该缓存里没有查找到该 childView 就先 findViewById 再缓存下来
     */
    private SparseArray<View> mCachedViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        final View iv = itemView;
        if (iv.isEnabled() && iv.isClickable())
            iv.setOnClickListener(getViewEventWatcher());
        if (iv.isEnabled() && iv.isLongClickable())
            iv.setOnLongClickListener(getViewEventWatcher());
        //注册 子 view 点击监听器
        int[] clickViewIds = onRegisterClickEvent();
        if (clickViewIds != null)
        for (int id : clickViewIds) {
            View v = getView(id);
            if (v != null) v.setOnClickListener(getViewEventWatcher());
        }
        //注册 子 view 长按监听器
        int[] longClickViewIds = onRegisterLongClickEvent();
        if (longClickViewIds != null)
        for (int id : longClickViewIds) {
            View v = getView(id);
            if (v != null) v.setOnLongClickListener(getViewEventWatcher());
        }
    }

    @Override
    public int[] onRegisterClickEvent() {return null;}

    @Override
    public void onItemClick(BaseViewHolder vh, View v) {}

    @Override
    public int[] onRegisterLongClickEvent() {return null;}

    @Override
    public boolean onItemLongClick(BaseViewHolder vh, View v) {return false;}

    /**
     * 根据 id 查找 ItemView 里 childView
     * @param id ItemView 里 childView 的 id
     * @return ItemView 里的 childView
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        if (id == View.NO_ID) return null;
        final View iv = itemView;
        View v = mCachedViews.get(id);
        if (v == null) {
            if (id == iv.getId()) {
                v = iv;
            } else {
                v = iv.findViewById(id);
            }
            if (v != null) mCachedViews.put(id, v);
        }
        return (T) v;
    }

    private ViewEventWatcher getViewEventWatcher() {
        if (mViewEventWatcher == null) {
            mViewEventWatcher = new ViewEventWatcher();
        }
        return mViewEventWatcher;
    }

    private class ViewEventWatcher implements View.OnClickListener, View.OnLongClickListener {
        @Override
        public void onClick(View v) {
            onItemClick(BaseViewHolder.this, v);
        }

        @Override
        public boolean onLongClick(View v) {
            return onItemLongClick(BaseViewHolder.this, v);
        }
    }
}
