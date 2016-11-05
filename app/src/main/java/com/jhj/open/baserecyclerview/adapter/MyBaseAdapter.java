package com.jhj.open.baserecyclerview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jhj.lib.baserecyclerview.adapter.BaseAdapter;
import com.jhj.open.baserecyclerview.model.Test;

/**
 * Created by jhj_Plus on 2016/11/2.
 */
public class MyBaseAdapter extends BaseAdapter<MyViewHolder, Test> {
    private static final String TAG = "MyBaseAdapter";

    public MyBaseAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(MyViewHolder vh, Test data, int position) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

}
