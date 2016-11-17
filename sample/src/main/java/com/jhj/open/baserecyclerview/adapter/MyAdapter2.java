package com.jhj.open.baserecyclerview.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhj.lib.baserecyclerview.viewholder.BaseViewHolder;
import com.jhj.open.baserecyclerview.R;

/**
 * Created by jhj_Plus on 2016/10/15.
 */
public class MyAdapter2 extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "MyAdapter2";
    private Context mContext;

    public MyAdapter2(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.item_tab,parent,false)){};
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public ItemDecoration getItemDecoration() {
        return new ItemDecoration();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state)
        {
            final int childAdapterPos = parent.getChildAdapterPosition(view);
            final int count = getItemCount();
            final int itemOffset = mContext.getResources().getDimensionPixelSize(
                    R.dimen.fab_margin);
            outRect.set(itemOffset, 0, childAdapterPos == count - 1 ? itemOffset : 0, 0);
        }
    }
}
