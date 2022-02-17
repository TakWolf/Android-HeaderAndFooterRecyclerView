package com.takwolf.android.hfrecyclerview.loadmorefooter;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import com.takwolf.android.hfrecyclerview.ProxyAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class LoadMoreFooter {
    public static final int STATE_DISABLED = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_ENDLESS = 3;
    public static final int STATE_FAILED = 4;

    @IntDef({STATE_DISABLED, STATE_LOADING, STATE_FINISHED, STATE_ENDLESS, STATE_FAILED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    @NonNull
    private final View footerView;

    @State
    private int state = STATE_DISABLED;

    @IntRange(from = 0)
    private int preloadOffset;

    @Nullable
    private HeaderAndFooterRecyclerView recyclerView;
    @Nullable
    private OnLoadMoreListener listener;

    public LoadMoreFooter(@NonNull View footerView) {
        this.footerView = footerView;
    }

    @NonNull
    public View getView() {
        return footerView;
    }

    @State
    public int getState() {
        return state;
    }

    public void setState(@State int state) {
        if (this.state != state) {
            this.state = state;
            onUpdateViews(footerView, state);
        }
    }

    @IntRange(from = 0)
    public int getPreloadOffset() {
        return preloadOffset;
    }

    public void setPreloadOffset(@IntRange(from = 0) int preloadOffset) {
        this.preloadOffset = preloadOffset;
    }

    public void setOnLoadMoreListener(@Nullable OnLoadMoreListener listener) {
        this.listener = listener;
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            handleOnScrolled(recyclerView);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            handleOnScrolled(recyclerView);
        }

        private void handleOnScrolled(@NonNull RecyclerView recyclerView) {
            if (!recyclerView.canScrollVertically(1)) {
                checkDoLoadMore();
            } else if (preloadOffset > 0 && recyclerView instanceof HeaderAndFooterRecyclerView) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    ProxyAdapter proxyAdapter = ((HeaderAndFooterRecyclerView) recyclerView).getProxyAdapter();
                    if (lastPosition >= proxyAdapter.getItemCount() - preloadOffset) {
                        checkDoLoadMore();
                    }
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
                    ProxyAdapter proxyAdapter = ((HeaderAndFooterRecyclerView) recyclerView).getProxyAdapter();
                    for (int lastPosition : lastPositions) {
                        if (lastPosition >= proxyAdapter.getItemCount() - preloadOffset) {
                            checkDoLoadMore();
                            break;
                        }
                    }
                }
            }
        }
    };

    public void addToRecyclerView(@NonNull HeaderAndFooterRecyclerView recyclerView) {
        if (this.recyclerView != null) {
            throw new IllegalStateException("LoadMoreFooter has been added.");
        }
        this.recyclerView = recyclerView;
        onUpdateViews(footerView, state);
        recyclerView.addFooterView(footerView);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public void removeFromRecyclerView() {
        if (recyclerView != null) {
            recyclerView.removeOnScrollListener(onScrollListener);
            recyclerView.removeFooterView(footerView);
            recyclerView = null;
        }
    }

    public void checkDoLoadMore() {
        if (state == STATE_ENDLESS || state == STATE_FAILED) {
            setState(STATE_LOADING);
            if (listener != null) {
                listener.onLoadMore();
            }
        }
    }

    protected abstract void onUpdateViews(@NonNull View footerView, @State int state);

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
