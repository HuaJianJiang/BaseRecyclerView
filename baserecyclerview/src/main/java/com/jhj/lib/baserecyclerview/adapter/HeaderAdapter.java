package com.jhj.lib.baserecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jhj.lib.baserecyclerview.interfaces.AdapterWrapper;
import com.jhj.lib.baserecyclerview.utils.Packager;
import com.jhj.lib.baserecyclerview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhj_Plus on 2016/10/10.
 */
public abstract class HeaderAdapter<HVH extends BaseViewHolder, FVH extends BaseViewHolder,
        H, F>
        extends RecyclerView.Adapter<BaseViewHolder> implements AdapterWrapper
{
    private static final String TAG = "HeaderAdapter";
    private static final int ITEM_VIEW_TYPE_UNKNOWN = -1;
    private static final int ITEM_VIEW_TYPE_DEFAULT = 0;
    protected Context context;
    protected LayoutInflater inflater;
    private RecyclerView.Adapter mAdapter;

    private List<H> mHeaders;
    private List<F> mFooters;

    public HeaderAdapter(Context ctxt, RecyclerView.Adapter adapter)
    {
        this(ctxt, adapter, null, null);
    }

    public HeaderAdapter(Context ctxt, RecyclerView.Adapter adapter, List<H> headers, List<F> footers)
    {
        context = ctxt;
        inflater = LayoutInflater.from(ctxt);
        mAdapter = adapter;
        mHeaders = headers == null ? new ArrayList<H>() : mHeaders;
        mFooters = footers == null ? new ArrayList<F>() : mFooters;
    }

    protected abstract HVH onCreateHeaderViewHolder(ViewGroup parent, int headerViewType);
    protected abstract void onBindHeaderViewHolder(HVH vh, H header, int position);

    protected abstract FVH onCreateFooterViewHolder(ViewGroup parent, int footerViewType);
    protected abstract void onBindFooterViewHolder(FVH vh, F footer, int position,int inPosition);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final int localViewType = Packager.getLocalViewType(viewType);
        final int clientViewType = Packager.getClientViewType(viewType);
        if (localViewType == Packager.ITEM_VIEW_TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, clientViewType);
        } else if (localViewType == Packager.ITEM_VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, clientViewType);
        } else {
            return mAdapter != null ? (BaseViewHolder) mAdapter.onCreateViewHolder(parent,
                    clientViewType) : null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isHeaderView(position)) {
            H header = position < mHeaders.size() ? mHeaders.get(position) : null;
            onBindHeaderViewHolder((HVH) holder, header, position);
        } else if (isFooterView(position)) {
            final int adjPos = position - getHeaderCount() - getWrappedItemCount();
            F footer = adjPos < mFooters.size() ? mFooters.get(adjPos) : null;
            onBindFooterViewHolder((FVH) holder, footer, position, adjPos);
        } else {
            if (mAdapter != null) {
                mAdapter.onBindViewHolder(holder, position - getHeaderCount());
            }
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getWrappedItemCount() + getFooterCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return Packager.makeItemViewTypeSpec(getHeaderViewType(position),
                    Packager.ITEM_VIEW_TYPE_HEADER);
        } else if (isFooterView(position)) {
            final int adjPos = position - getHeaderCount() - getWrappedItemCount();
            return Packager.makeItemViewTypeSpec(getFooterViewType(position, adjPos),
                    Packager.ITEM_VIEW_TYPE_FOOTER);
        } else {
            final int adjPos = position - getHeaderCount();
            return Packager.makeItemViewTypeSpec(getClientItemViewType(adjPos),
                    Packager.ITEM_VIEW_TYPE_CLIENT);
        }
    }

    public boolean isHeaderView(int position) {
        return getHeaderCount() > 0 && position < getHeaderCount();
    }

    public boolean isItemView(int position) {
        return position >= getHeaderCount() && position < getItemCount()-getFooterCount();
    }

    public boolean isFooterView(int position) {
        return getFooterCount() > 0 && position >= getHeaderCount() + getWrappedItemCount() &&
                position < getItemCount();
    }

    private int getClientItemViewType(int position) {
        return mAdapter != null ? mAdapter.getItemViewType(position) : ITEM_VIEW_TYPE_UNKNOWN;
    }

    public int getHeaderViewType(int position) {
        return ITEM_VIEW_TYPE_DEFAULT;
    }

    public int getFooterViewType(int position, int inPosition) {
        return ITEM_VIEW_TYPE_DEFAULT;
    }

    protected int getWrappedItemCount() {
        return mAdapter != null ? mAdapter.getItemCount() : 0;
    }

    public boolean containHeader() {
        return !mHeaders.isEmpty();
    }

    public boolean containFooter() {
        return !mFooters.isEmpty();
    }

    public int getHeaderCount() {
        return mHeaders.size();
    }

    public int getFooterCount() {
        return mFooters.size();
    }

    @Override
    public RecyclerView.Adapter getWrappedAdapter() {
        return mAdapter;
    }

    public List<H> getHeaders() {
        return mHeaders;
    }

    public List<F> getFooters() {
        return mFooters;
    }

    public H getHeader(int position) {
        return mHeaders.get(position);
    }

    public F getFooter(int position) {
        return mFooters.get(position);
    }

    public void insertHeader(H header) {
        insertHeader(header, false);
    }

    public void insertHeader(H header,boolean reverse) {
        insertHeader(reverse ? 0 : mHeaders.size(), header);
    }

    public void insertHeader(int position, H header) {
        mHeaders.add(position, header);
        notifyItemInserted(position);
    }

    public void insertHeaders(List<H> headers) {
        insertHeaders(headers,false);
    }

    public void insertHeaders(List<H> headers, boolean reverse) {
        insertHeaders(reverse ? 0 : mHeaders.size(), headers);
    }

    public void insertHeaders(int position, List<H> headers) {
        mHeaders.addAll(position, headers);
        notifyItemRangeInserted(position, headers.size());
    }

    public H removeHeader(int position) {
        H removedHeader = mHeaders.remove(position);
        notifyItemRemoved(position);
        return removedHeader;
    }

    public void removeHeader(H header) {
        final int removedPos = mHeaders.indexOf(header);
        mHeaders.remove(header);
        notifyItemRemoved(removedPos);
    }

    public void removeHeaders(List<H> headers) {
        final int removedPosStart = mHeaders.indexOf(headers.get(0));
        final int removedCount=headers.size();
        mHeaders.removeAll(headers);
        notifyItemRangeRemoved(removedPosStart, removedCount);
    }

    public void changeHeader(int position, H newHeader) {
        mHeaders.set(position, newHeader);
        notifyItemChanged(position);
    }

    public void moveHeader(int fromPosition, int toPosition) {
        H moveHeader = mHeaders.remove(fromPosition);
        mHeaders.add(toPosition, moveHeader);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void insertFooter(F footer) {
        insertFooter(footer, false);
    }

    public void insertFooter(F footer,boolean reverse) {
        insertFooter(reverse ? 0 : mFooters.size(), footer);
    }

    public void insertFooter(int position, F footer) {
        mFooters.add(position, footer);
        notifyItemInserted(getHeaderCount() + getWrappedItemCount() + position);
    }

    public void insertFooters(List<F> footers) {
        insertFooters(footers,false);
    }

    public void insertFooters(List<F> footers, boolean reverse) {
        insertFooters(reverse ? 0 : mFooters.size(), footers);
    }

    public void insertFooters(int position, List<F> footers) {
        mFooters.addAll(position, footers);
        notifyItemRangeInserted(getHeaderCount() + getWrappedItemCount() + position,
                footers.size());
    }

    public F removeFooter(int position) {
        F removedFooter = mFooters.remove(position);
        notifyItemRemoved(getHeaderCount() + getWrappedItemCount() + position);
        return removedFooter;
    }

    public void removeFooter(F footer) {
        final int removedPos = mFooters.indexOf(footer);
        mFooters.remove(removedPos);
        notifyItemRemoved(getHeaderCount() + getWrappedItemCount() + removedPos);
    }

    public void removeFooters(List<F> footers) {
        final int removedPosStart = mFooters.indexOf(footers.get(0));
        final int removedCount=footers.size();
        mFooters.removeAll(footers);
        notifyItemRangeRemoved(getHeaderCount() + getWrappedItemCount() + removedPosStart,
                removedCount);
    }

    public void changeFooter(int position, F newFooter) {
        mFooters.set(position, newFooter);
        notifyItemChanged(getHeaderCount() + getWrappedItemCount() + position);
    }

    public void moveFooter(int fromPosition, int toPosition) {
        F moveFooter = mFooters.remove(fromPosition);
        mFooters.add(toPosition, moveFooter);
        notifyItemMoved(getHeaderCount() + getWrappedItemCount() + fromPosition,
                getHeaderCount() + getWrappedItemCount() + toPosition);
    }
}
