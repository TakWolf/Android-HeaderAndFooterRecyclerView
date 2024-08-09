package com.takwolf.android.demo.hfrecyclerview.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter

class PagingSource(
    private val doRefresh: PagingSource.(dataVersion: Int) -> Unit,
    private val doLoadMore: PagingSource.(dataVersion: Int) -> Unit,
) {
    private var dataVersion = 0

    private val refreshState = MutableLiveData(false)
    private val loadMoreState = MutableLiveData(LoadMoreFooter.STATE_DISABLED)

    fun setupViews(
        owner: LifecycleOwner,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
    ) {
        refreshLayout.setOnRefreshListener {
            refresh()
        }
        loadMoreFooter.setOnLoadMoreListener {
            loadMore()
        }
        refreshState.observe(owner) { isRefreshing ->
            refreshLayout.isRefreshing = isRefreshing
        }
        loadMoreState.observe(owner) { state ->
            loadMoreFooter.state = state
        }
    }

    fun checkDataVersion(dataVersion: Int): Boolean {
        return this.dataVersion == dataVersion
    }

    fun refresh() {
        val isRefreshing = refreshState.value ?: false
        if (isRefreshing) {
            return
        }
        refreshState.value = true
        doRefresh(dataVersion)
    }

    fun onRefreshSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            this.dataVersion += 1
            refreshState.value = false
            loadMoreState.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            return true
        } else {
            return false
        }
    }

    fun onRefreshFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            refreshState.value = false
            return true
        } else {
            return false
        }
    }

    fun loadMore() {
        val state = loadMoreState.value ?: LoadMoreFooter.STATE_DISABLED
        if (state == LoadMoreFooter.STATE_DISABLED || state == LoadMoreFooter.STATE_LOADING || state == LoadMoreFooter.STATE_FINISHED) {
            return
        }
        loadMoreState.value = LoadMoreFooter.STATE_LOADING
        doLoadMore(dataVersion)
    }

    fun onLoadMoreSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = if (isFinished) LoadMoreFooter.STATE_FINISHED else LoadMoreFooter.STATE_ENDLESS
            return true
        } else {
            return false
        }
    }

    fun onLoadMoreFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            loadMoreState.value = LoadMoreFooter.STATE_FAILED
            return true
        } else {
            return false
        }
    }
}
