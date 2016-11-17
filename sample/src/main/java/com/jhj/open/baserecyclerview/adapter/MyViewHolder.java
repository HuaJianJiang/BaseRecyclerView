package com.jhj.open.baserecyclerview.adapter;

import android.view.View;
import android.widget.Toast;

import com.jhj.lib.baserecyclerview.viewholder.BaseViewHolder;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public class MyViewHolder extends BaseViewHolder {
    private static final String TAG = "MyViewHolder";

    public MyViewHolder(View itemView) {
        super(itemView);

    }

    @Override
    protected void onItemClick(BaseViewHolder vh, View v, int position) {
        Toast.makeText(v.getContext(), "onItemClick=>"+position, Toast.LENGTH_SHORT).show();
    }
}
