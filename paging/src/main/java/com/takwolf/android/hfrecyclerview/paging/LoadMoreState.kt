package com.takwolf.android.hfrecyclerview.paging

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class LoadMoreState(val canLoadMore: Boolean) {
    DISABLED(false),
    IDLE(true),
    LOADING(false),
    FINISHED(false),
    FAILED(true),
}

fun StateFlow<LoadMoreState>.observe(
    owner: LifecycleOwner,
    loadMoreFooter: LoadMoreFooter,
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { state ->
                loadMoreFooter.state = state
            }
        }
    }
}
