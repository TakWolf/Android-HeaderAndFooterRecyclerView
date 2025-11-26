package com.takwolf.android.hfrecyclerview.paging

import kotlinx.coroutines.flow.MutableStateFlow

abstract class PagingSource {
    val refreshState = MutableStateFlow(RefreshState.IDLE)
    val loadMoreState = MutableStateFlow(LoadMoreState.DISABLED)

    private var dataVersion = 0

    fun checkDataVersion(dataVersion: Int): Boolean {
        return this.dataVersion == dataVersion
    }

    fun invalidDataVersion() {
        dataVersion += 1
    }

    fun refresh() {
        if (refreshState.value.canRefresh) {
            refreshState.value = RefreshState.LOADING
            doRefresh(dataVersion)
        }
    }

    protected abstract fun doRefresh(dataVersion: Int)

    protected fun onRefreshSuccess(dataVersion: Int, isFinished: Boolean): Boolean {
        if (checkDataVersion(dataVersion)) {
            invalidDataVersion()
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
        if (loadMoreState.value.canLoadMore) {
            loadMoreState.value = LoadMoreState.LOADING
            doLoadMore(dataVersion)
        }
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
}
