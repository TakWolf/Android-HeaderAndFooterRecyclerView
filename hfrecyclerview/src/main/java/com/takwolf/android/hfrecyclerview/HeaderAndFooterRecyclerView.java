package com.takwolf.android.hfrecyclerview;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.HackRecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class HeaderAndFooterRecyclerView extends HackRecyclerView {
    final List<View> headerViews = new ArrayList<>();
    final List<View> footerViews = new ArrayList<>();

    private final ProxyAdapter proxyAdapter = new ProxyAdapter(this);

    private boolean keepScrollPositionOnItemInserted = true;

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

    @NonNull
    public View addHeaderView(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, getHeaderViewContainer(), false);
        addHeaderView(view);
        return view;
    }

    @NonNull
    public View addHeaderView(@LayoutRes int layoutId, int index) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, getHeaderViewContainer(), false);
        addHeaderView(view, index);
        return view;
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

    @NonNull
    public View addFooterView(@LayoutRes int layoutId) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, getFooterViewContainer(), false);
        addFooterView(view);
        return view;
    }

    @NonNull
    public View addFooterView(@LayoutRes int layoutId, int index) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, getFooterViewContainer(), false);
        addFooterView(view, index);
        return view;
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

    public boolean isKeepScrollPositionOnItemInserted() {
        return keepScrollPositionOnItemInserted;
    }

    public void setKeepScrollPositionOnItemInserted(boolean keepScrollPositionOnItemInserted) {
        this.keepScrollPositionOnItemInserted = keepScrollPositionOnItemInserted;
    }

    public static final class PositionWithOffset {
        public final int position;
        public final int offset;

        public PositionWithOffset(int position, int offset) {
            this.position = position;
            this.offset = offset;
        }
    }

    @Nullable
    public PositionWithOffset getScrollPositionWithOffset() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
            RecyclerView.ViewHolder holder = findViewHolderForLayoutPosition(firstPosition);
            if (holder != null) {
                int offset;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                if (linearLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    if ((getLayoutDirection() == View.LAYOUT_DIRECTION_LTR && linearLayoutManager.getReverseLayout()) || (getLayoutDirection() == View.LAYOUT_DIRECTION_RTL && !linearLayoutManager.getReverseLayout())) {
                        offset = getWidth() - holder.itemView.getRight() - layoutParams.rightMargin - getPaddingRight();
                    } else {
                        offset = holder.itemView.getLeft() - layoutParams.leftMargin - getPaddingLeft();
                    }
                } else {
                    if (linearLayoutManager.getReverseLayout()) {
                        offset = getHeight() - holder.itemView.getBottom() - layoutParams.bottomMargin - getPaddingBottom();
                    } else {
                        offset = holder.itemView.getTop() - layoutParams.topMargin - getPaddingTop();
                    }
                }
                return new PositionWithOffset(firstPosition, offset);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int firstPosition = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[0];
            RecyclerView.ViewHolder holder = findViewHolderForLayoutPosition(firstPosition);
            if (holder != null) {
                int offset;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                if (staggeredGridLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    if ((getLayoutDirection() == View.LAYOUT_DIRECTION_LTR && staggeredGridLayoutManager.getReverseLayout()) || (getLayoutDirection() == View.LAYOUT_DIRECTION_RTL && !staggeredGridLayoutManager.getReverseLayout())) {
                        offset = getWidth() - holder.itemView.getRight() - layoutParams.rightMargin - getPaddingRight();
                    } else {
                        offset = holder.itemView.getLeft() - layoutParams.leftMargin - getPaddingLeft();
                    }
                } else {
                    if (staggeredGridLayoutManager.getReverseLayout()) {
                        offset = getHeight() - holder.itemView.getBottom() - layoutParams.bottomMargin - getPaddingBottom();
                    } else {
                        offset = holder.itemView.getTop() - layoutParams.topMargin - getPaddingTop();
                    }
                }
                return new PositionWithOffset(firstPosition, offset);
            }
        }
        return null;
    }

    public void scrollToPositionWithOffset(int position, int offset) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, offset);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(position, offset);
        }
    }

    @Override
    protected int getAdapterPositionInRecyclerView(@NonNull ViewHolder viewHolder) {
        if (viewHolder instanceof FixedViewHolder) {
            return NO_POSITION;
        }
        int globalPosition = super.getAdapterPositionInRecyclerView(viewHolder);
        if (globalPosition == NO_POSITION) {
            return NO_POSITION;
        }
        return globalPosition - proxyAdapter.getPositionOffset();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        for (View view : headerViews) {
            SparseArray<Parcelable> container = new SparseArray<>();
            view.saveHierarchyState(container);
            savedState.headerStates.add(container);
        }
        for (View view : footerViews) {
            SparseArray<Parcelable> container = new SparseArray<>();
            view.saveHierarchyState(container);
            savedState.footerStates.add(container);
        }
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        if (savedState.headerStates.size() == headerViews.size()) {
            for (int i = 0; i < savedState.headerStates.size(); i++) {
                SparseArray<Parcelable> container = savedState.headerStates.get(i);
                View view = headerViews.get(i);
                view.restoreHierarchyState(container);
            }
        }
        if (savedState.footerStates.size() == footerViews.size()) {
            for (int i = 0; i < savedState.footerStates.size(); i++) {
                SparseArray<Parcelable> container = savedState.footerStates.get(i);
                View view = footerViews.get(i);
                view.restoreHierarchyState(container);
            }
        }
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    private static class SavedState extends BaseSavedState {
        private final List<SparseArray<Parcelable>> headerStates = new ArrayList<>();
        private final List<SparseArray<Parcelable>> footerStates = new ArrayList<>();

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            readValues(source, null);
        }

        @RequiresApi(Build.VERSION_CODES.N)
        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readValues(source, loader);
        }

        private void readValues(@NonNull Parcel source, @Nullable ClassLoader loader) {
            int headersCount = source.readInt();
            for (int i = 0; i < headersCount; i++) {
                SparseArray<Parcelable> container = source.readSparseArray(loader);
                headerStates.add(container);
            }
            int footersCount = source.readInt();
            for (int i = 0; i < footersCount; i++) {
                SparseArray<Parcelable> container = source.readSparseArray(loader);
                footerStates.add(container);
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(headerStates.size());
            for (SparseArray<Parcelable> container : headerStates) {
                out.writeSparseArray(container);
            }
            out.writeInt(footerStates.size());
            for (SparseArray<Parcelable> container : footerStates) {
                out.writeSparseArray(container);
            }
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return new SavedState(source, loader);
                } else {
                    return new SavedState(source);
                }
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
