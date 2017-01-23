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

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<BVH extends BaseViewHolder, T> extends RecyclerView.Adapter<BVH> {
    private static final String TAG = "BaseAdapter";
    protected Context ctxt;
    protected LayoutInflater inflater;
    private List<T> mItems;

    public BaseAdapter(Context ctxt) {
        this(ctxt, null);
    }

    public BaseAdapter(Context ctxt, List<T> items) {
        this.ctxt = ctxt;
        inflater = LayoutInflater.from(ctxt);
        mItems = items == null ? new ArrayList<T>() : items;
    }

    public abstract void onPopulateViewHolder(BVH vh, T item, int position);

    @Override
    public void onBindViewHolder(BVH holder, int position) {
        T item = position < mItems.size() ? mItems.get(position) : null;
        onPopulateViewHolder(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<T> getItems() {
        return mItems;
    }

    public void invalidateItems(List<T> newItems) {
        if (newItems == null) mItems.clear();
        else mItems = newItems;
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

}
