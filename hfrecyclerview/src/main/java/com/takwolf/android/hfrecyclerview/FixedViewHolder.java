package com.takwolf.android.hfrecyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

final class FixedViewHolder extends RecyclerView.ViewHolder {
    static FixedViewHolder create(@NonNull Context context) {
        return new FixedViewHolder(new LinearLayout(context));
    }

    @NonNull
    private final LinearLayout viewContainer;

    private FixedViewHolder(@NonNull LinearLayout viewContainer) {
        super(viewContainer);
        this.viewContainer = viewContainer;
    }

    @NonNull
    LinearLayout getViewContainer() {
        return viewContainer;
    }

    private void adjustViewContainerLayoutParamsAndOrientation(@Nullable RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.LayoutParams layoutParams;
            int orientation;
            if (viewContainer.getLayoutParams() instanceof GridLayoutManager.LayoutParams) {
                layoutParams = (GridLayoutManager.LayoutParams) viewContainer.getLayoutParams();
                if (gridLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    orientation = LinearLayout.HORIZONTAL;
                } else {
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    orientation = LinearLayout.VERTICAL;
                }
            } else {
                if (gridLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    layoutParams = new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    orientation = LinearLayout.HORIZONTAL;
                } else {
                    layoutParams = new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    orientation = LinearLayout.VERTICAL;
                }
            }
            viewContainer.setLayoutParams(layoutParams);
            viewContainer.setOrientation(orientation);
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            RecyclerView.LayoutParams layoutParams;
            int orientation;
            if (viewContainer.getLayoutParams() instanceof RecyclerView.LayoutParams) {
                layoutParams = (RecyclerView.LayoutParams) viewContainer.getLayoutParams();
                if (linearLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    orientation = LinearLayout.HORIZONTAL;
                } else {
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    orientation = LinearLayout.VERTICAL;
                }
            } else {
                if (linearLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    orientation = LinearLayout.HORIZONTAL;
                } else {
                    layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    orientation = LinearLayout.VERTICAL;
                }
            }
            viewContainer.setLayoutParams(layoutParams);
            viewContainer.setOrientation(orientation);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            StaggeredGridLayoutManager.LayoutParams layoutParams;
            int orientation;
            if (viewContainer.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewContainer.getLayoutParams();
                if (staggeredGridLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    orientation = LinearLayout.HORIZONTAL;
                } else {
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    orientation = LinearLayout.VERTICAL;
                }
            } else {
                if (staggeredGridLayoutManager.getOrientation() == RecyclerView.HORIZONTAL) {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    orientation = LinearLayout.HORIZONTAL;
                } else {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    orientation = LinearLayout.VERTICAL;
                }
            }
            layoutParams.setFullSpan(true);
            viewContainer.setLayoutParams(layoutParams);
            viewContainer.setOrientation(orientation);
        }
    }

    void bind(@Nullable RecyclerView.LayoutManager layoutManager, @NonNull List<View> views) {
        viewContainer.removeAllViews();
        adjustViewContainerLayoutParamsAndOrientation(layoutManager);
        for (View view : views) {
            if (view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            viewContainer.addView(view);
        }
    }

    void bind(@Nullable RecyclerView.LayoutManager layoutManager, @NonNull UpdateInfo updateInfo) {
        adjustViewContainerLayoutParamsAndOrientation(layoutManager);
        switch (updateInfo.action) {
            case UpdateInfo.ACTION_ADD:
                if (updateInfo.index == null) {
                    viewContainer.addView(updateInfo.view);
                } else {
                    viewContainer.addView(updateInfo.view, updateInfo.index);
                }
                break;
            case UpdateInfo.ACTION_REMOVE:
                if (updateInfo.index == null) {
                    viewContainer.removeView(updateInfo.view);
                } else {
                    viewContainer.removeViewAt(updateInfo.index);
                }
                break;
            default:
                throw new IllegalArgumentException("Impossible update info action.");
        }
    }

    static final class UpdateInfo {
        static final int ACTION_ADD = 0;
        static final int ACTION_REMOVE = 1;

        @IntDef({ACTION_ADD, ACTION_REMOVE})
        @Retention(RetentionPolicy.SOURCE)
        @interface Action {}

        @Action
        private final int action;
        @NonNull
        private final View view;
        @Nullable
        private final Integer index;

        UpdateInfo(@Action int action, @NonNull View view, @Nullable Integer index) {
            this.action = action;
            this.view = view;
            this.index = index;
        }
    }
}
