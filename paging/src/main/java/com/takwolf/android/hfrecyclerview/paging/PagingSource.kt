package com.takwolf.android.hfrecyclerview.paging

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class PagingSource {
    private val refreshState = MutableStateFlow(RefreshState.IDLE)
    private val loadMoreState = MutableStateFlow(LoadMoreState.DISABLED)

    private var dataVersion = 0

    protected fun checkDataVersion(dataVersion: Int): Boolean {
        return this.dataVersion == dataVersion
    }

    fun refresh() {
        if (refreshState.value == RefreshState.LOADING) {
            return
        }
        refreshState.value = RefreshState.LOADING
        doRefresh(dataVersion)
    }

    protected abstract fun doRefresh(dataVersion: Int)

    protected fun onRefreshSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            this.dataVersion += 1
            refreshState.value = RefreshState.IDLE
            loadMoreState.value = if (isFinished) LoadMoreState.FINISHED else LoadMoreState.IDLE
            return true
        } else {
            return false
        }
    }

    protected fun onRefreshFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            refreshState.value = RefreshState.FAILED
            return true
        } else {
            return false
        }
    }

    fun loadMore() {
        if (loadMoreState.value == LoadMoreState.DISABLED ||
            loadMoreState.value == LoadMoreState.LOADING ||
            loadMoreState.value == LoadMoreState.FINISHED) {
            return
        }
        loadMoreState.value = LoadMoreState.LOADING
        doLoadMore(dataVersion)
    }

    protected abstract fun doLoadMore(dataVersion: Int)

    protected fun onLoadMoreSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = if (isFinished) LoadMoreState.FINISHED else LoadMoreState.IDLE
            return true
        } else {
            return false
        }
    }

    protected fun onLoadMoreFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = LoadMoreState.FAILED
            return true
        } else {
            return false
        }
    }

    fun setupSwipeRefreshLayout(
        owner: LifecycleOwner,
        refreshLayout: SwipeRefreshLayout,
    ) {
        refreshLayout.setOnRefreshListener {
            refresh()
        }

        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                refreshState.collect { state ->
                    refreshLayout.isRefreshing = state == RefreshState.LOADING
                }
            }
        }
    }

    fun setupLoadMoreFooter(
        owner: LifecycleOwner,
        loadMoreFooter: LoadMoreFooter,
    ) {
        loadMoreFooter.onLoadMoreListener = LoadMoreFooter.OnLoadMoreListener {
            loadMore()
        }

        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadMoreState.collect { state ->
                    loadMoreFooter.state = state
                }
            }
        }
    }
}