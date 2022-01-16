package com.takwolf.android.hfrecyclerview.loadmorefooter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

public abstract class LoadMoreFooter {
    private final View footerView;

    @LoadMoreState
    private int state = LoadMoreState.DISABLED;

    private int preloadOffset;

    @Nullable
    private OnLoadMoreListener listener;

    public LoadMoreFooter(@NonNull View footerView) {
        this.footerView = footerView;
    }

    @NonNull
    public View getView() {
        return footerView;
    }

    @LoadMoreState
    public int getState() {
        return state;
    }

    public void setState(@LoadMoreState int state) {
        if (this.state != state) {
            this.state = state;
            onUpdateViews(footerView, state);
        }
    }

    public int getPreloadOffset() {
        return preloadOffset;
    }

    public void setPreloadOffset(int preloadOffset) {
        this.preloadOffset = preloadOffset;
    }

    public void setOnLoadMoreListener(@Nullable OnLoadMoreListener listener) {
        this.listener = listener;
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            check(recyclerView);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            check(recyclerView);
        }

        private void check(@NonNull RecyclerView recyclerView) {
            if (!recyclerView.canScrollVertically(1)) {
                checkDoLoadMore();
            }
        }
    };

    public void addToRecyclerView(@NonNull HeaderAndFooterRecyclerView recyclerView) {
        onUpdateViews(footerView, state);
        recyclerView.addFooterView(footerView);
        recyclerView.addOnScrollListener(onScrollListener);
        // TODO
    }

    public void removeFromRecyclerView(@NonNull HeaderAndFooterRecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(onScrollListener);
        recyclerView.removeFooterView(footerView);
    }

    public void checkDoLoadMore() {
        if (state == LoadMoreState.ENDLESS || state == LoadMoreState.FAILED) {
            setState(LoadMoreState.LOADING);
            if (listener != null) {
                listener.onLoadMore();
            }
        }
    }

    protected abstract void onUpdateViews(@NonNull View footerView, @LoadMoreState int state);

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
