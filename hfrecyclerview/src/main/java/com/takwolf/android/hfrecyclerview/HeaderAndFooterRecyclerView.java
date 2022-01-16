package com.takwolf.android.hfrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.HeaderAndFooterHackRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeaderAndFooterRecyclerView extends HeaderAndFooterHackRecyclerView {
    final List<View> headerViews = new ArrayList<>();
    final List<View> footerViews = new ArrayList<>();

    private final ProxyAdapter proxyAdapter = new ProxyAdapter(this);

    public HeaderAndFooterRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public HeaderAndFooterRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderAndFooterRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inspectLayoutManager(getLayoutManager());
        super.setAdapter(proxyAdapter);
    }

    @NonNull
    private LinearLayout getFixedViewContainer(@ProxyAdapter.ViewType int viewType) {
        ViewHolder holder = getRecycledViewPool().getRecycledView(viewType);
        if (holder == null) {
            holder = FixedViewHolder.create(getContext());
        }
        getRecycledViewPool().putRecycledView(holder);
        if (holder instanceof FixedViewHolder) {
            return ((FixedViewHolder) holder).getViewContainer();
        } else {
            throw new IllegalStateException("Impossible fixed view holder type.");
        }
    }

    @NonNull
    public ViewGroup getHeaderViewContainer() {
        return getFixedViewContainer(ProxyAdapter.VIEW_TYPE_HEADER);
    }

    @NonNull
    public ViewGroup getFooterViewContainer() {
        return getFixedViewContainer(ProxyAdapter.VIEW_TYPE_FOOTER);
    }

    public void addHeaderView(@NonNull View view) {
        headerViews.add(view);
        proxyAdapter.notifyHeaderViewAdded(view, null);
    }

    public void addHeaderView(@NonNull View view, int index) {
        headerViews.add(index, view);
        proxyAdapter.notifyHeaderViewAdded(view, index);
    }

    @Nullable
    public View getHeaderViewAt(int index) {
        if (index < 0 || index >= headerViews.size()) {
            return null;
        }
        return headerViews.get(index);
    }

    public int getHeaderViewsCount() {
        return headerViews.size();
    }

    public void removeHeaderView(@NonNull View view) {
        if (headerViews.remove(view)) {
            proxyAdapter.notifyHeaderViewRemoved(view, null);
        }
    }

    public void removeHeaderView(int index) {
        View view = headerViews.remove(index);
        proxyAdapter.notifyHeaderViewRemoved(view, index);
    }

    public void addFooterView(@NonNull View view) {
        footerViews.add(view);
        proxyAdapter.notifyFooterViewAdded(view, null);
    }

    public void addFooterView(@NonNull View view, int index) {
        footerViews.add(index, view);
        proxyAdapter.notifyFooterViewAdded(view, index);
    }

    @Nullable
    public View getFooterViewAt(int index) {
        if (index < 0 || index >= footerViews.size()) {
            return null;
        }
        return footerViews.get(index);
    }

    public int getFooterViewsCount() {
        return footerViews.size();
    }

    public void removeFooterView(@NonNull View view) {
        if (footerViews.remove(view)) {
            proxyAdapter.notifyFooterViewRemoved(view, null);
        }
    }

    public void removeFooterView(int index) {
        View view = footerViews.remove(index);
        proxyAdapter.notifyFooterViewRemoved(view, index);
    }

    @Override
    public void swapAdapter(@Nullable Adapter adapter, boolean removeAndRecycleExistingViews) {
        super.swapAdapter(null, removeAndRecycleExistingViews);
        proxyAdapter.setAdapter(adapter);
        super.swapAdapter(proxyAdapter, removeAndRecycleExistingViews);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(null);
        proxyAdapter.setAdapter(adapter);
        super.setAdapter(proxyAdapter);
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    @Override
    public Adapter getAdapter() {
        return proxyAdapter.getAdapter();
    }

    @NonNull
    public ProxyAdapter getProxyAdapter() {
        return proxyAdapter;
    }

    private void inspectLayoutManager(@Nullable LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            FixedViewSpanSizeLookup fixedViewSpanSizeLookup = null;
            if (spanSizeLookup == null || spanSizeLookup instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                fixedViewSpanSizeLookup = new FixedViewSpanSizeLookup();
                gridLayoutManager.setSpanSizeLookup(fixedViewSpanSizeLookup);
            } else if (spanSizeLookup instanceof FixedViewSpanSizeLookup) {
                fixedViewSpanSizeLookup = (FixedViewSpanSizeLookup) spanSizeLookup;
            }
            if (fixedViewSpanSizeLookup != null) {
                fixedViewSpanSizeLookup.attach(gridLayoutManager, proxyAdapter);
            }
        }
    }

    private void recoverLayoutManager(@Nullable LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            if (spanSizeLookup instanceof FixedViewSpanSizeLookup) {
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.DefaultSpanSizeLookup());
                ((FixedViewSpanSizeLookup) spanSizeLookup).detach();
            }
        }
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layoutManager) {
        LayoutManager oldLayoutManager = getLayoutManager();
        inspectLayoutManager(layoutManager);
        super.setLayoutManager(layoutManager);
        recoverLayoutManager(oldLayoutManager);
    }

    @Override
    protected final int hackGetAdapterPositionInRecyclerView(ViewHolder holder) {
        if (holder instanceof FixedViewHolder) {
            return NO_POSITION;
        }
        int globalPosition = super.hackGetAdapterPositionInRecyclerView(holder);
        if (globalPosition == NO_POSITION) {
            return NO_POSITION;
        }
        return globalPosition - proxyAdapter.getPositionOffset();
    }
}
