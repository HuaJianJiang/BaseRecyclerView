package com.jhj.lib.baserecyclerview.view;

import android.support.v7.widget.GridLayoutManager;

import com.jhj.lib.baserecyclerview.adapter.HeaderAdapter;

/**
 * Created by jhj_Plus on 2016/11/4.
 */
public final class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private static final String TAG = "HeaderSpanSizeLookup";
    private HeaderAdapter mHeaderAdapter;
    private int mHeaderSpanCount;

    public HeaderSpanSizeLookup(HeaderAdapter headerAdapter, int headerSpanCount) {
        setSpanIndexCacheEnabled(true);
        mHeaderAdapter = headerAdapter;
        mHeaderSpanCount = headerSpanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (mHeaderAdapter.isItemView(position)) {
            return getItemSpanSize(position);
        }
        return mHeaderSpanCount;
    }

    protected int getItemSpanSize(int position) {
        return 1;
    }
}
