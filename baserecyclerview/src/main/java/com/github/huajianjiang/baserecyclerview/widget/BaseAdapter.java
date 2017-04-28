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
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.github.huajianjiang.baserecyclerview.util.Predications;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<BVH extends BaseViewHolder, T> extends RecyclerView
        .Adapter<BVH> implements Filterable {
    private static final String TAG = "BaseAdapter";
    private Context mCtxt;
    private LayoutInflater mInflater;
    private List<T> mItems;

    private BaseFilter mFilter;
    private List<T> mOriginalItems;

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
        mCtxt = ctxt;
        mInflater = LayoutInflater.from(ctxt);
        mItems = items == null ? new ArrayList<T>() : items;
    }

    public abstract BVH onBuildViewHolder(ViewGroup parent, int viewType);
    public abstract void onPopulateViewHolder(BVH holder, int position);

    @Override
    public BVH onCreateViewHolder(ViewGroup parent, int viewType) {
        BVH bvh = onBuildViewHolder(parent, viewType);
        RecyclerView rv = parent instanceof RecyclerView ? (RecyclerView) parent : null;
        bvh.connectAdapter(rv, BaseAdapter.this);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BVH holder, int position) {
        onPopulateViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Context getContext() {
        return mCtxt;
    }

    public LayoutInflater getLayoutInflater() {
        return mInflater;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void invalidate(List<T> newItems) {
        mItems.clear();
        if (mOriginalItems != null) {
            mOriginalItems.clear();
        }
        if (newItems != null) {
            mItems = newItems;
            if (mOriginalItems != null) {
                mOriginalItems.addAll(newItems);
            }
        }
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public int getPosition(@Nullable T item) {
        return mItems.indexOf(item);
    }

    public void insert(T item) {
        insert(item, false);
    }

    public void insert(T item, boolean reverse) {
        insert(reverse ? 0 : mItems.size(), item);
    }

    public void insert(int position, T item) {
        mItems.add(position, item);
        if (mOriginalItems != null) {
            mOriginalItems.add(position, item);
        }
        notifyItemInserted(position);
    }

    public void insertAll(List<T> items) {
        insertAll(items, false);
    }

    public void insertAll(List<T> items, boolean reverse) {
        insertAll(reverse ? 0 : mItems.size(), items);
    }

    public void insertAll(int position, List<T> items) {
        mItems.addAll(position, items);
        if (mOriginalItems != null) {
            mOriginalItems.addAll(position, items);
        }
        notifyItemRangeInserted(position, items.size());
    }

    public T remove(int position) {
        T removedItem = mItems.remove(position);
        if (mOriginalItems != null) {
            mOriginalItems.remove(position);
        }
        notifyItemRemoved(position);
        return removedItem;
    }

    public void remove(T item) {
        final int removePos = mItems.indexOf(item);
        remove(removePos);
    }

    public void removeAll(List<T> items) {
        final int removedPosStart = mItems.indexOf(items.get(0));
        final int removedCount = items.size();
        mItems.removeAll(items);
        if (mOriginalItems != null) {
            mOriginalItems.removeAll(items);
        }
        notifyItemRangeRemoved(removedPosStart, removedCount);
    }

    public void change(int position, T newItem) {
        mItems.set(position, newItem);
        if (mOriginalItems != null) {
            mOriginalItems.set(position, newItem);
        }
        notifyItemChanged(position);
    }

    public void move(int fromPosition, int toPosition) {
        T moveItem = mItems.remove(fromPosition);
        mItems.add(toPosition, moveItem);
        if (mOriginalItems != null) {
            T origMoveItem = mOriginalItems.remove(fromPosition);
            mOriginalItems.add(toPosition, origMoveItem);
        }
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


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new BaseFilter();
        }
        return mFilter;
    }

    /**
     *
     */
    private class BaseFilter extends Filter {

        BaseFilter() {
            if (mOriginalItems == null) {
                mOriginalItems = new ArrayList<>(mItems);
            }
        }

        public void restore() {
            mItems = mOriginalItems;
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return super.convertResultToString(resultValue);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // 返回的查询结果
            FilterResults results = new FilterResults();

            if (Predications.isNullOrEmpty(constraint)) {
                results.values = null;
                results.count = 0;
            } else {
                List<T> originalCopy = new ArrayList<>(mOriginalItems);
                //TODO

            }
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<T> filteredItems = (List<T>) results.values;
            if (filteredItems != null) {
                mItems = filteredItems;
            } else {
                mItems.clear();
            }
            notifyDataSetChanged();
        }
    }
}
