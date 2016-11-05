package com.jhj.open.baserecyclerview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jhj.lib.baserecyclerview.adapter.BaseAdapter;
import com.jhj.open.baserecyclerview.R;

import java.util.List;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public class MyAdapter extends BaseAdapter<MyViewHolder,String> {
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<String> items) {
        super(context, items);
    }

    @Override
    protected void onBindViewHolder(MyViewHolder vh, String data, int position) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item, parent, false));
    }
}
