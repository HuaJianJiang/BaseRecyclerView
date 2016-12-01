/*
 * Copyright (c) 2016 HuaJian Jiang
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

package com.github.huajianjiang.baserecyclerview.sample.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.huajianjiang.baserecyclerview.adapter.HeaderAdapter;
import com.github.huajianjiang.baserecyclerview.sample.R;
import com.github.huajianjiang.baserecyclerview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by jhj_Plus on 2016/10/12.
 */
public class MyHeaderAdapter extends HeaderAdapter<BaseViewHolder, BaseViewHolder, Integer, Integer>
{
    private static final String TAG = "MyHeaderAdapter";
    private static final int TYPE_HEADER_1 = 0;
    private static final int TYPE_FOOTER_1 = 1;

    private Random mRandom = new Random();

    public MyHeaderAdapter(Context context, MyAdapter adapter) {
        super(context, adapter, new ArrayList<>(Arrays.asList(1, 0, 1)),
                new ArrayList<>(Arrays.asList(0, 1, 0)));
    }

    public void addRandomHeader() {
        insertHeader(mRandom.nextInt(2), false);
    }

    public void addRandomFooter() {
        insertFooter(mRandom.nextInt(2),true);
    }

    @Override
    public BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(
                inflater.inflate(viewType == TYPE_HEADER_1 ? R.layout.header_1 : R.layout.header_2,
                        parent, false))
        {
            @Override
            protected void onItemClick(BaseViewHolder vh, View v, int position) {
                Toast.makeText(context, "onHeaderClick=>"+position, Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    protected void onBindHeaderViewHolder(BaseViewHolder vh, Integer data, int position) {

    }

    @Override
    public BaseViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(context)
                .inflate(viewType == TYPE_FOOTER_1 ? R.layout.footer_1 : R.layout.footer_2, parent,
                        false)) {
            @Override
            protected void onItemClick(BaseViewHolder vh, View v, int position) {
                Toast.makeText(context, "onFooterClick=>"+position, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onBindFooterViewHolder(BaseViewHolder vh, Integer data, int position,
            int inPosition)
    {

    }

    @Override
    public int getHeaderViewType(int position) {
        return getHeader(position);
    }

    @Override
    public int getFooterViewType(int position, int inPosition) {
        return getFooter(inPosition);
    }

    public ItemDecoration getItemDecoration() {
        return new ItemDecoration();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        final int itemOffset = context.getResources().getDimensionPixelSize(
                R.dimen.fab_margin);
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state)
        {
            final int childAdapterPos = parent.getChildAdapterPosition(view);
            MyHeaderAdapter adapter= (MyHeaderAdapter) parent.getAdapter();
            GridLayoutManager layoutManager=(GridLayoutManager)parent.getLayoutManager();
            final int spanCount = layoutManager.getSpanCount();
            outRect.set(itemOffset,
                    adapter.isHeaderView(childAdapterPos) ? childAdapterPos == 0 ? itemOffset : 0
                            : adapter.containHeader() ? 0
                                    : childAdapterPos < spanCount + adapter.getHeaderCount()
                                            ? itemOffset : 0, itemOffset, itemOffset);
        }
    }

}
