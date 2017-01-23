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
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.huajianjiang.baserecyclerview.R;

/**
 * Created by jhj_Plus on 2016/10/11.
 */
public class BaseRecyclerView extends RecyclerView {
    private static final String TAG = "BaseRecyclerView";
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
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView);
        boolean nestedScrollingEnabled = ta
                .getBoolean(R.styleable.BaseRecyclerView_nestedScrollingEnabled, true);
        boolean hasFixedSize = ta.getBoolean(R.styleable.BaseRecyclerView_hasFixedSize, false);
        mHeaderFooterFullSpan = ta
                .getBoolean(R.styleable.BaseRecyclerView_header_footer_fullSpan, true);
        ta.recycle();

        setHasFixedSize(hasFixedSize);
        setNestedScrollingEnabled(nestedScrollingEnabled);
        setFocusable(nestedScrollingEnabled);
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mHeaderFooterFullSpan && layout instanceof GridLayoutManager) {
            GridLayoutManager gridLayout = (GridLayoutManager) layout;
            Adapter adapter = getAdapter();
            if (adapter instanceof MultipleHeaderAdapter) {
                MultipleHeaderAdapter multipleHeaderAdapter = (MultipleHeaderAdapter) adapter;
                gridLayout.setSpanSizeLookup(
                        new HeaderSpanSizeLookup(multipleHeaderAdapter, gridLayout.getSpanCount()));
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mHeaderFooterFullSpan && adapter instanceof MultipleHeaderAdapter) {
            MultipleHeaderAdapter multipleHeaderAdapter = (MultipleHeaderAdapter) adapter;
            LayoutManager layout = getLayoutManager();
            if (layout instanceof GridLayoutManager) {
                GridLayoutManager gridLayout = (GridLayoutManager) layout;
                gridLayout.setSpanSizeLookup(
                        new HeaderSpanSizeLookup(multipleHeaderAdapter, gridLayout.getSpanCount()));
            }
        }
    }

}
