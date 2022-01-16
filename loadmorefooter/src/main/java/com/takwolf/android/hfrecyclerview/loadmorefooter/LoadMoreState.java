package com.takwolf.android.hfrecyclerview.loadmorefooter;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({LoadMoreState.DISABLED, LoadMoreState.LOADING, LoadMoreState.FINISHED, LoadMoreState.ENDLESS, LoadMoreState.FAILED})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadMoreState {
    int DISABLED = 0;
    int LOADING = 1;
    int FINISHED = 2;
    int ENDLESS = 3;
    int FAILED = 4;
}
