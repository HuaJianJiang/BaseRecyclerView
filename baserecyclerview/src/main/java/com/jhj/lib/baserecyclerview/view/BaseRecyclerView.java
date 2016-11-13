package com.jhj.lib.baserecyclerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jhj.lib.baserecyclerview.R;
import com.jhj.lib.baserecyclerview.adapter.HeaderAdapter;

/**
 * Created by jhj_Plus on 2016/10/11.
 */
public class BaseRecyclerView extends RecyclerView {
    private static final String TAG = "BaseRecyclerView";
    private boolean mHasFixedSize = false;
    private boolean mHeaderFooterFullSpan = true;

    public BaseRecyclerView(Context context) {
        this(context, null);
    }
    
    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs,defStyle);
        initView();
    }
    
    private void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView);
        mHasFixedSize = ta.getBoolean(R.styleable.BaseRecyclerView_hasFixedSize, false);
        mHeaderFooterFullSpan = ta.getBoolean(
                R.styleable.BaseRecyclerView_header_footer_fullSpan, true);
        ta.recycle();
    }

    private void initView() {
        setHasFixedSize(mHasFixedSize);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mHeaderFooterFullSpan && layout instanceof GridLayoutManager) {
            GridLayoutManager gridLayout = (GridLayoutManager) layout;
            Adapter adapter = getAdapter();
            if (adapter instanceof HeaderAdapter) {
                HeaderAdapter headerAdapter = (HeaderAdapter) adapter;
                gridLayout.setSpanSizeLookup(
                        new HeaderSpanSizeLookup(headerAdapter, gridLayout.getSpanCount()));
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mHeaderFooterFullSpan && adapter instanceof HeaderAdapter) {
            HeaderAdapter headerAdapter = (HeaderAdapter) adapter;
            LayoutManager layout = getLayoutManager();
            if (layout instanceof GridLayoutManager) {
                GridLayoutManager gridLayout = (GridLayoutManager) layout;
                gridLayout.setSpanSizeLookup(
                        new HeaderSpanSizeLookup(headerAdapter, gridLayout.getSpanCount()));
            }
        }
    }



}
