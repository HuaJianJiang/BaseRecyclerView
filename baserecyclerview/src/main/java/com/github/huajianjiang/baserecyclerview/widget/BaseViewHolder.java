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

    private RecyclerView mRv;

    /**
     * 与之关联的适配器
     */
    private BaseAdapter mAdapter;
    /**
     * ItemView 的 childView 缓存
     * 便于根据 id 查找对应的 View
     * 如果该缓存里没有查找到该 childView 就先 findViewById 再缓存下来
     */
    private SparseArray<View> mCachedViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    private void initView() {
        if (mAdapter == null) return;
        final View iv = itemView;
        if (iv.isEnabled() && iv.isClickable())
            iv.setOnClickListener(mAdapter.getViewEventWatcher());
        if (iv.isEnabled() && iv.isLongClickable())
            iv.setOnLongClickListener(mAdapter.getViewEventWatcher());
        //注册 子 view 点击监听器
        int[] clickViewIds = onRegisterClickEvent(mRv);
        if (clickViewIds != null)
        for (int id : clickViewIds) {
            View v = getView(id);
            if (v != null) v.setOnClickListener(mAdapter.getViewEventWatcher());
        }
        //注册 子 view 长按监听器
        int[] longClickViewIds = onRegisterLongClickEvent(mRv);
        if (longClickViewIds != null)
        for (int id : longClickViewIds) {
            View v = getView(id);
            if (v != null) v.setOnLongClickListener(mAdapter.getViewEventWatcher());
        }
    }

    @Override
    public int[] onRegisterClickEvent(RecyclerView rv){return null;}

    @Override
    public void onItemClick(RecyclerView rv, View v){}

    @Override
    public int[] onRegisterLongClickEvent(RecyclerView rv) {return null;}

    @Override
    public boolean onItemLongClick(RecyclerView rv, View v) {return false;}

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

    /**
     * 获得与之关联的 {@link BaseAdapter}
     */
    public BaseAdapter getAssociateAdapter() {
        return mAdapter;
    }

    void connectAdapter(RecyclerView rv, BaseAdapter adapter) {
        this.mRv = rv;
        this.mAdapter = adapter;
        initView();
    }
}
