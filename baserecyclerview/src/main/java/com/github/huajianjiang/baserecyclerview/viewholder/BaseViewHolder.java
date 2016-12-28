package com.github.huajianjiang.baserecyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.github.huajianjiang.baserecyclerview.interfaces.ViewHolderCallback;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements ViewHolderCallback {
    private static final String TAG = "BaseViewHolder";

    /**
     * ItemView 的 childView 缓存
     * 便于根据 id 查找对应的 View
     * 如果该缓存里没有查找到该 childView 就先 findViewById 再缓存下来
     */
    private SparseArray<View> mCachedViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
        init();
    }

    private void init() {
        ViewEventListener listener = new ViewEventListener();
        //设置监听 itemView 点击事件
        if (itemView.isClickable()) itemView.setOnClickListener(listener);
        //注册 子 view 点击监听器
        int[] clickViewIds = onRegisterClickEvent();
        if (clickViewIds != null)
        for (int id : clickViewIds) {
            View v = getView(id);
            if (v != null) v.setOnClickListener(listener);
        }
        //注册 子 view 长按监听器
        int[] longClickViewIds = onRegisterLongClickEvent();
        if (longClickViewIds != null)
        for (int id : longClickViewIds) {
            View v = getView(id);
            if (v != null) v.setOnLongClickListener(listener);
        }
    }

    @Override
    public int[] onRegisterClickEvent() {return null;}

    @Override
    public void onItemClick(BaseViewHolder vh, View v, int adapterPosition) {}

    @Override
    public int[] onRegisterLongClickEvent() {return null;}

    @Override
    public boolean onItemLongClick(BaseViewHolder vh, View v, int adapterPosition) {return false;}

    /**
     * 根据 id 查找 ItemView 里 childView
     * @param id ItemView 里 childView 的 id
     * @return ItemView 里的 childView
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        if (id == View.NO_ID) return null;
        View v = mCachedViews.get(id);
        if (v == null) {
            if (id == itemView.getId()) {
                v = itemView;
            } else {
                v = itemView.findViewById(id);
            }
            if (v != null) mCachedViews.put(id, v);
        }
        return (T) v;
    }

    private class ViewEventListener implements View.OnClickListener, View.OnLongClickListener {
        @Override
        public void onClick(View v) {
           onItemClick(BaseViewHolder.this, v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return onItemLongClick(BaseViewHolder.this, v, getAdapterPosition());
        }
    }
}
