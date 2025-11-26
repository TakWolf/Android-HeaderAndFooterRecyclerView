package com.takwolf.android.hfrecyclerview.paging

import kotlinx.coroutines.flow.MutableStateFlow

abstract class RefreshSource {
    val state = MutableStateFlow(RefreshState.IDLE)

    private var dataVersion = 0

    fun checkDataVersion(dataVersion: Int): Boolean {
        return this.dataVersion == dataVersion
    }

    fun invalidDataVersion() {
        dataVersion += 1
    }

    fun refresh() {
        if (state.value.canRefresh) {
            state.value = RefreshState.LOADING
            doRefresh(dataVersion)
        }
    }

    protected abstract fun doRefresh(dataVersion: Int)

    protected fun onRefreshSuccess(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            invalidDataVersion()
            state.value = RefreshState.IDLE
            return true
        } else {
            return false
        }
    }

    protected fun onRefreshFailure(dataVersion: Int): Boolean {
        if (checkDataVersion(dataVersion)) {
            state.value = RefreshState.FAILED
            return true
        } else {
            return false
        }
    }
}
