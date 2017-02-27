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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<BVH extends BaseViewHolder, T> extends RecyclerView.Adapter<BVH> {
    private static final String TAG = "BaseAdapter";

    protected Context ctxt;

    protected LayoutInflater inflater;

    private List<T> mItems;

    /**
     * 当前所有监听适配器的 RecyclerView 集合
     */
    private List<RecyclerView> mAttachedRecyclerViews = new ArrayList<>(2);

    /**
     * itemView 或者 itemView 的子 view 交互事件监听器
     */
    private ViewEventWatcher mViewEventWatcher;

    public BaseAdapter(Context ctxt) {
        this(ctxt, null);
    }

    public BaseAdapter(Context ctxt, List<T> items) {
        this.ctxt = ctxt;
        inflater = LayoutInflater.from(ctxt);
        mItems = items == null ? new ArrayList<T>() : items;
    }

    public abstract BVH onCreateBaseViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindBaseViewHolder(BVH holder, int position);

    @Override
    public BVH onCreateViewHolder(ViewGroup parent, int viewType) {
        BVH bvh = onCreateBaseViewHolder(parent, viewType);
        RecyclerView rv = parent instanceof RecyclerView ? (RecyclerView) parent : null;
        bvh.connectAdapter(rv, BaseAdapter.this);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BVH holder, int position) {
        onBindBaseViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<T> getItems() {
        return mItems;
    }

    public void invalidateItems(List<T> newItems) {
        mItems.clear();
        if (newItems != null) mItems = newItems;
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public void insertItem(T item) {
        insertItem(item, false);
    }

    public void insertItem(T item, boolean reverse) {
        insertItem(reverse ? 0 : mItems.size(), item);
    }

    public void insertItem(int position, T item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void insertItems(List<T> items) {
        insertItems(items,false);
    }

    public void insertItems(List<T> items, boolean reverse) {
        insertItems(reverse ? 0 : mItems.size(), items);
    }

    public void insertItems(int position, List<T> items) {
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public T removeItem(int position) {
        T removedItem = mItems.remove(position);
        notifyItemRemoved(position);
        return removedItem;
    }

    public void removeItem(T item) {
        final int removedPos = mItems.indexOf(item);
        mItems.remove(item);
        notifyItemRemoved(removedPos);
    }

    public void removeItems(List<T> items) {
        final int removedPosStart = mItems.indexOf(items.get(0));
        final int removedCount = items.size();
        mItems.removeAll(items);
        notifyItemRangeRemoved(removedPosStart, removedCount);
    }

    public void changeItem(int position, T newItem) {
        mItems.set(position, newItem);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        T moveItem = mItems.remove(fromPosition);
        mItems.add(toPosition, moveItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mAttachedRecyclerViews.add(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mAttachedRecyclerViews.remove(recyclerView);
    }

    ViewEventWatcher getViewEventWatcher() {
        if (mViewEventWatcher == null) {
            mViewEventWatcher = new ViewEventWatcher();
        }
        return mViewEventWatcher;
    }

    private class ViewEventWatcher implements View.OnClickListener, View.OnLongClickListener {
        @Override
        public void onClick(View v) {
            for (RecyclerView parent : mAttachedRecyclerViews) {
                BaseViewHolder vh = (BaseViewHolder) parent.findContainingViewHolder(v);
                if (vh != null) {
                    vh.onItemClick(parent, v);
                    break;
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            boolean handled = false;
            for (RecyclerView parent : mAttachedRecyclerViews) {
                BaseViewHolder vh = (BaseViewHolder) parent.findContainingViewHolder(v);
                if (vh != null) {
                    handled = vh.onItemLongClick(parent, v);
                    break;
                }
            }
            return handled;
        }
    }
}
