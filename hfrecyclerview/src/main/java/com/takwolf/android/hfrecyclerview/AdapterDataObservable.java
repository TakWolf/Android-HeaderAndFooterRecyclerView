package com.takwolf.android.hfrecyclerview;

import android.database.Observable;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

final class AdapterDataObservable extends Observable<RecyclerView.AdapterDataObserver> {
    public void onChanged() {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onChanged();
        }
    }

    public void onItemRangeChanged(int positionStart, int itemCount) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeChanged(positionStart, itemCount);
        }
    }

    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeChanged(positionStart, itemCount, payload);
        }
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeInserted(positionStart, itemCount);
        }
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeRemoved(positionStart, itemCount);
        }
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    }

    public void onStateRestorationPolicyChanged() {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onStateRestorationPolicyChanged();
        }
    }
}
