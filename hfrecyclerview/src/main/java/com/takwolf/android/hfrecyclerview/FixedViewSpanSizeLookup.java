package com.takwolf.android.hfrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

final class FixedViewSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    @Nullable
    private GridLayoutManager layoutManager;
    @Nullable
    private ProxyAdapter proxyAdapter;

    void attach(@NonNull GridLayoutManager layoutManager, @NonNull ProxyAdapter proxyAdapter) {
        if (this.layoutManager != null || this.proxyAdapter != null) {
            throw new IllegalStateException("FixedViewSpanSizeLookup can not be shared.");
        }
        this.layoutManager = layoutManager;
        this.proxyAdapter = proxyAdapter;
    }

    void detach() {
        layoutManager = null;
        proxyAdapter = null;
    }

    @Override
    public int getSpanSize(int position) {
        if (layoutManager == null || proxyAdapter == null) {
            throw new IllegalStateException("FixedViewSpanSizeLookup has not been attached yet.");
        }
        if (proxyAdapter.isHeaderViewHolderPosition(position) || proxyAdapter.isFooterViewHolderPosition(position)) {
            return layoutManager.getSpanCount();
        } else {
            return 1;
        }
    }
}
