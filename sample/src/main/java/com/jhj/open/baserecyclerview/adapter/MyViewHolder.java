package com.jhj.open.baserecyclerview.adapter;

import android.view.View;
import android.widget.Toast;

import com.jhj.lib.baserecyclerview.viewholder.BaseViewHolder;
import com.jhj.open.baserecyclerview.R;

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

    @Override
    protected int[] onRegisterLongClickEvent() {
        return new int[]{R.id.item};
    }

    @Override
    protected boolean onItemLongClick(BaseViewHolder vh, View v, int adapterPosition) {
        Toast.makeText(v.getContext(), "onItemLongClick=>" + adapterPosition, Toast.LENGTH_SHORT)
                .show();
        return true;
    }
}
