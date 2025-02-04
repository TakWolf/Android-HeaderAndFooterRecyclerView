package com.takwolf.android.hfrecyclerview;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public final class ProxyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_HEADER = Integer.MIN_VALUE;
    public static final int VIEW_TYPE_FOOTER = VIEW_TYPE_HEADER + 1;

    @IntDef({VIEW_TYPE_HEADER, VIEW_TYPE_FOOTER})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {}

    private static final long ITEM_ID_HEADER = Long.MIN_VALUE;
    private static final long ITEM_ID_FOOTER = ITEM_ID_HEADER + 1;

    @NonNull
    private final HeaderAndFooterRecyclerView recyclerView;

    @SuppressWarnings("rawtypes")
    @Nullable
    private RecyclerView.Adapter adapter;

    ProxyAdapter(@NonNull HeaderAndFooterRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @SuppressWarnings("rawtypes")
    void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        if (this.adapter != null) {
            this.adapter.unregisterAdapterDataObserver(innerAdapterDataObserver);
            this.adapter.onDetachedFromRecyclerView(recyclerView);
        }
        this.adapter = adapter;
        boolean hasStableIds;
        StateRestorationPolicy stateRestorationPolicy;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(innerAdapterDataObserver);
            adapter.onAttachedToRecyclerView(recyclerView);
            hasStableIds = adapter.hasStableIds();
            stateRestorationPolicy = adapter.getStateRestorationPolicy();
        } else {
            hasStableIds = false;
            stateRestorationPolicy = StateRestorationPolicy.ALLOW;
        }
        if (hasStableIds() != hasStableIds) {
            super.setHasStableIds(hasStableIds);
        }
        if (getStateRestorationPolicy() != stateRestorationPolicy) {
            super.setStateRestorationPolicy(stateRestorationPolicy);
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        throw new UnsupportedOperationException("Calling setHasStableIds is not allowed on the ProxyAdapter.");
    }

    @Override
    public void setStateRestorationPolicy(@NonNull StateRestorationPolicy strategy) {
        throw new UnsupportedOperationException("Calling setStateRestorationPolicy is not allowed on the ProxyAdapter.");
    }

    private boolean isShowHeaderViewHolder() {
        return recyclerView.getHeaderViewsCount() > 0;
    }

    private boolean isShowFooterViewHolder() {
        return recyclerView.getFooterViewsCount() > 0;
    }

    boolean isHeaderViewHolderPosition(int position) {
        return isShowHeaderViewHolder() && position == 0;
    }

    boolean isFooterViewHolderPosition(int position) {
        return isShowFooterViewHolder() && position == getItemCount() - 1;
    }

    int getPositionOffset() {
        return isShowHeaderViewHolder() ? 1 : 0;
    }

    private int getHeaderViewHolderNotifyPosition() {
        return 0;
    }

    private int getFooterViewHolderNotifyPosition() {
        return getPositionOffset() + (adapter == null ? 0 : adapter.getItemCount());
    }

    void notifyHeaderViewAdded(@NonNull View view, @Nullable Integer index) {
        if (recyclerView.getHeaderViewsCount() == 1) {
            notifyItemInserted(getHeaderViewHolderNotifyPosition());
        } else {
            notifyItemChanged(getHeaderViewHolderNotifyPosition(), new FixedViewHolder.UpdateInfo(FixedViewHolder.UpdateInfo.ACTION_ADD, view, index));
        }
    }

    void notifyHeaderViewRemoved(@NonNull View view, @Nullable Integer index) {
        if (recyclerView.getHeaderViewsCount() == 0) {
            notifyItemRemoved(getHeaderViewHolderNotifyPosition());
        } else {
            notifyItemChanged(getHeaderViewHolderNotifyPosition(), new FixedViewHolder.UpdateInfo(FixedViewHolder.UpdateInfo.ACTION_REMOVE, view, index));
        }
    }

    void notifyFooterViewAdded(@NonNull View view, @Nullable Integer index) {
        if (recyclerView.getFooterViewsCount() == 1) {
            notifyItemInserted(getFooterViewHolderNotifyPosition());
        } else {
            notifyItemChanged(getFooterViewHolderNotifyPosition(), new FixedViewHolder.UpdateInfo(FixedViewHolder.UpdateInfo.ACTION_ADD, view, index));
        }
    }

    void notifyFooterViewRemoved(@NonNull View view, @Nullable Integer index) {
        if (recyclerView.getFooterViewsCount() == 0) {
            notifyItemRemoved(getFooterViewHolderNotifyPosition());
        } else {
            notifyItemChanged(getFooterViewHolderNotifyPosition(), new FixedViewHolder.UpdateInfo(FixedViewHolder.UpdateInfo.ACTION_REMOVE, view, index));
        }
    }

    private final RecyclerView.AdapterDataObserver innerAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart + getPositionOffset(), itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            notifyItemRangeChanged(positionStart + getPositionOffset(), itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart + getPositionOffset(), itemCount);
            if (recyclerView.isKeepScrollPositionOnItemInserted()) {
                HeaderAndFooterRecyclerView.PositionWithOffset positionWithOffset = recyclerView.getScrollPositionWithOffset();
                if (positionWithOffset != null) {
                    recyclerView.scrollToPositionWithOffset(positionWithOffset.position, positionWithOffset.offset);
                }
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart + getPositionOffset(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (itemCount != 1) {
                throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
            }
            notifyItemMoved(fromPosition + getPositionOffset(), toPosition + getPositionOffset());
        }

        @Override
        public void onStateRestorationPolicyChanged() {
            if (adapter != null) {
                setStateRestorationPolicy(adapter.getStateRestorationPolicy());
            }
        }
    };

    @Override
    public int getItemCount() {
        return (adapter == null ? 0 : adapter.getItemCount()) + (isShowHeaderViewHolder() ? 1 : 0) + (isShowFooterViewHolder() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewHolderPosition(position)) {
            return VIEW_TYPE_HEADER;
        } else if (isFooterViewHolderPosition(position)) {
            return VIEW_TYPE_FOOTER;
        } else {
            if (adapter != null) {
                int viewType = adapter.getItemViewType(position - getPositionOffset());
                if (viewType == VIEW_TYPE_HEADER) {
                    throw new IllegalStateException(VIEW_TYPE_HEADER + " is already used for view type Header, please replace another value.");
                } else if (viewType == VIEW_TYPE_FOOTER) {
                    throw new IllegalStateException(VIEW_TYPE_FOOTER + " is already used for view type Footer, please replace another value.");
                } else {
                    return viewType;
                }
            } else {
                throw new IllegalStateException("Inner adapter has not been set.");
            }
        }
    }

    @Override
    public long getItemId(int position) {
        if (isHeaderViewHolderPosition(position)) {
            return hasStableIds() ? ITEM_ID_HEADER : RecyclerView.NO_ID;
        } else if (isFooterViewHolderPosition(position)) {
            return hasStableIds() ? ITEM_ID_FOOTER : RecyclerView.NO_ID;
        } else {
            if (adapter != null) {
                long itemId = adapter.getItemId(position - getPositionOffset());
                if (itemId == ITEM_ID_HEADER) {
                    throw new IllegalStateException(ITEM_ID_HEADER + " is already used for item id Header, please replace another value.");
                } else if (itemId == ITEM_ID_FOOTER) {
                    throw new IllegalStateException(ITEM_ID_FOOTER + " is already used for item id Footer, please replace another value.");
                } else {
                    return itemId;
                }
            } else {
                throw new IllegalStateException("Inner adapter has not been set.");
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
                return FixedViewHolder.create(parent.getContext());
            default:
                if (adapter != null) {
                    return adapter.onCreateViewHolder(parent, viewType);
                } else {
                    throw new IllegalStateException("Inner adapter has not been set.");
                }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (holder instanceof FixedViewHolder) {
            if (payloads.isEmpty()) {
                if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
                    ((FixedViewHolder) holder).bind(recyclerView.getLayoutManager(), recyclerView.headerViews);
                } else if (holder.getItemViewType() == VIEW_TYPE_FOOTER) {
                    ((FixedViewHolder) holder).bind(recyclerView.getLayoutManager(), recyclerView.footerViews);
                } else {
                    throw new IllegalStateException("Impossible fixed view type.");
                }
            } else {
                for (Object payload : payloads) {
                    if (payload instanceof FixedViewHolder.UpdateInfo) {
                        ((FixedViewHolder) holder).bind(recyclerView.getLayoutManager(), (FixedViewHolder.UpdateInfo) payload);
                    } else {
                        throw new IllegalStateException("Impossible payload type.");
                    }
                }
            }
        } else if (adapter != null) {
            // noinspection unchecked
            adapter.bindViewHolder(holder, position - getPositionOffset());
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FixedViewHolder) {
            if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
                ((FixedViewHolder) holder).bind(recyclerView.getLayoutManager(), recyclerView.headerViews);
            } else if (holder.getItemViewType() == VIEW_TYPE_FOOTER) {
                ((FixedViewHolder) holder).bind(recyclerView.getLayoutManager(), recyclerView.footerViews);
            } else {
                throw new IllegalStateException("Impossible fixed view type.");
            }
        } else if (adapter != null) {
            // noinspection unchecked
            adapter.bindViewHolder(holder, position - getPositionOffset());
        } else {
            throw new IllegalStateException("Inner adapter has not been set.");
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (!(holder instanceof FixedViewHolder)) {
            if (adapter != null) {
                // noinspection unchecked
                adapter.onViewRecycled(holder);
            } else {
                throw new IllegalStateException("Inner adapter has not been set.");
            }
        }
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        if (!(holder instanceof FixedViewHolder)) {
            if (adapter != null) {
                // noinspection unchecked
                return adapter.onFailedToRecycleView(holder);
            } else {
                throw new IllegalStateException("Inner adapter has not been set.");
            }
        } else {
            return super.onFailedToRecycleView(holder);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (!(holder instanceof FixedViewHolder)) {
            if (adapter != null) {
                // noinspection unchecked
                adapter.onViewAttachedToWindow(holder);
            } else {
                throw new IllegalStateException("Inner adapter has not been set.");
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (!(holder instanceof FixedViewHolder)) {
            if (adapter != null) {
                // noinspection unchecked
                adapter.onViewDetachedFromWindow(holder);
            } else {
                throw new IllegalStateException("Inner adapter has not been set.");
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if (this.recyclerView != recyclerView) {
            throw new IllegalStateException("ProxyAdapter can not be attached to other RecyclerView.");
        }
    }

    @Override
    public int findRelativeAdapterPositionIn(@NonNull RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, @NonNull RecyclerView.ViewHolder viewHolder, int localPosition) {
        if (adapter == this && viewHolder instanceof FixedViewHolder) {
            return localPosition;
        } else if (adapter == this.adapter) {
            return adapter.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition - getPositionOffset());
        } else {
            return RecyclerView.NO_POSITION;
        }
    }
}
