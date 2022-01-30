package com.takwolf.android.hfrecyclerview.loadmorefooter;

import android.view.View;
import android.view.ViewGroup;

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

    private final RecyclerView.AdapterDataObserver innerAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (recyclerView != null) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForLayoutPosition(firstPosition);
                    if (holder != null) {
                        int offset;
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                        if (linearLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                            if (recyclerView.getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                                offset = holder.itemView.getLeft() - layoutParams.leftMargin;
                            } else {
                                offset = holder.itemView.getRight() - layoutParams.rightMargin;
                            }
                        } else {
                            offset = holder.itemView.getTop() - layoutParams.topMargin;
                        }
                        linearLayoutManager.scrollToPositionWithOffset(firstPosition, offset);
                    }
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int firstPosition = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForLayoutPosition(firstPosition);
                    if (holder != null) {
                        int offset;
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                        if (staggeredGridLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                            if (recyclerView.getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                                offset = holder.itemView.getLeft() - layoutParams.leftMargin;
                            } else {
                                offset = holder.itemView.getRight() - layoutParams.rightMargin;
                            }
                        } else {
                            offset = holder.itemView.getTop() - layoutParams.topMargin;
                        }
                        staggeredGridLayoutManager.scrollToPositionWithOffset(firstPosition, offset);
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
        recyclerView.getProxyAdapter().registerInnerAdapterDataObserver(innerAdapterDataObserver);
    }

    public void removeFromRecyclerView() {
        if (recyclerView != null) {
            recyclerView.getProxyAdapter().unregisterInnerAdapterDataObserver(innerAdapterDataObserver);
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
