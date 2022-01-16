package com.takwolf.android.hfrecyclerview.loadmorefooter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import com.takwolf.android.hfrecyclerview.ProxyAdapter;

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
            handleScrolled(recyclerView);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            handleScrolled(recyclerView);
        }

        private void handleScrolled(@NonNull RecyclerView recyclerView) {
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

    private final RecyclerView.AdapterDataObserver targetAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            // TODO
        }
    };

    public void addToRecyclerView(@NonNull HeaderAndFooterRecyclerView recyclerView) {
        onUpdateViews(footerView, state);
        recyclerView.addFooterView(footerView);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.getProxyAdapter().registerTargetAdapterDataObserver(targetAdapterDataObserver);
    }

    public void removeFromRecyclerView(@NonNull HeaderAndFooterRecyclerView recyclerView) {
        recyclerView.getProxyAdapter().unregisterTargetAdapterDataObserver(targetAdapterDataObserver);
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
