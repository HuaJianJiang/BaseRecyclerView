package com.github.huajianjiang.baserecyclerview.view;

import android.support.v7.widget.GridLayoutManager;

import com.github.huajianjiang.baserecyclerview.adapter.HeaderAdapter;

/**
 * Created by jhj_Plus on 2016/11/4.
 */
public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private static final String TAG = "HeaderSpanSizeLookup";
    private HeaderAdapter mHeaderAdapter;
    private int mSpanCount;

    public HeaderSpanSizeLookup(HeaderAdapter headerAdapter, int spanCount) {
        setSpanIndexCacheEnabled(true);
        mHeaderAdapter = headerAdapter;
        mSpanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (mHeaderAdapter.isHeaderView(position)) {
            return getHeaderSpanSize(position);
        } else if (mHeaderAdapter.isFooterView(position)) {
            return getFooterSpanSize(position);
        }
        return getItemSpanSize(position);
    }

    protected int getHeaderSpanSize(int position) {
        return mSpanCount;
    }

    protected int getItemSpanSize(int position) {
        return 1;
    }

    protected int getFooterSpanSize(int position) {
        return mSpanCount;
    }
}
