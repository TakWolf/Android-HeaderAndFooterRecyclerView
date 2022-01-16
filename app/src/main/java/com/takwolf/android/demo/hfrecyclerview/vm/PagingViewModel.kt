package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class PagingViewModel<Entity> : ListViewModel<Entity>() {
    val refreshStateData = MutableLiveData(false)
    val loadMoreStateData = MutableLiveData(LoadMoreState.DISABLED)
    private var isRefreshDoing = false
    private var isLoadMoreDoing = false
    private var isFinished = false
    private var version = 0

    fun refresh() {
        if (!isRefreshDoing) {
            refreshStateData.value = true
            isRefreshDoing = true
            viewModelScope.launch(Dispatchers.IO) {
                doRefresh()
            }
        }
    }

    abstract suspend fun doRefresh();

    protected fun refreshSuccess(entities: MutableList<Entity>, @Suppress("SameParameterValue") isFinished: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            entitiesData.value = entities
            this@PagingViewModel.isFinished = isFinished
            version += 1
            isRefreshDoing = false
            refreshStateData.value = false
            isLoadMoreDoing = false
            loadMoreStateData.value = if (isFinished) LoadMoreState.FINISHED else LoadMoreState.ENDLESS
        }
    }

    protected fun refreshFailure() {
        viewModelScope.launch(Dispatchers.Main) {
            isRefreshDoing = false
            refreshStateData.value = false
        }
    }

    fun loadMore() {
        if (!isLoadMoreDoing && !isFinished) {
            loadMoreStateData.value = LoadMoreState.LOADING
            isLoadMoreDoing = true
            viewModelScope.launch(Dispatchers.IO) {
                doLoadMore(version)
            }
        }
    }

    abstract suspend fun doLoadMore(version: Int);

    protected fun loadMoreSuccess(version: Int, entities: MutableList<Entity>, isFinished: Boolean) {
        if (this.version == version) {
            viewModelScope.launch(Dispatchers.Main) {
                entitiesData.value?.let { it ->
                    it.addAll(entities)
                    entitiesData.value = it
                }
                this@PagingViewModel.isFinished = isFinished
                isLoadMoreDoing = false
                loadMoreStateData.value = if (isFinished) LoadMoreState.FINISHED else LoadMoreState.ENDLESS
            }
        }
    }

    protected fun loadMoreFailure(version: Int) {
        if (this.version == version) {
            viewModelScope.launch(Dispatchers.Main) {
                isLoadMoreDoing = false
                loadMoreStateData.value = LoadMoreState.FAILED
            }
        }
    }
}
