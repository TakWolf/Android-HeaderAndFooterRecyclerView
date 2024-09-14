package com.takwolf.android.hfrecyclerview.paging

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class RefreshState(val canRefresh: Boolean) {
    DISABLED(false),
    IDLE(true),
    LOADING(false),
    FAILED(true),
}

fun StateFlow<RefreshState>.observe(
    owner: LifecycleOwner,
    refreshLayout: SwipeRefreshLayout,
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { state ->
                when (state) {
                    RefreshState.DISABLED -> {
                        refreshLayout.isEnabled = false
                        refreshLayout.isRefreshing = false
                    }
                    else -> {
                        refreshLayout.isEnabled = true
                        refreshLayout.isRefreshing = state == RefreshState.LOADING
                    }
                }
            }
        }
    }
}
