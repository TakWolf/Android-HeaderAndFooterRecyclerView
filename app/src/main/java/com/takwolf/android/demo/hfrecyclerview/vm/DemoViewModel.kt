package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.hfrecyclerview.model.Banner
import com.takwolf.android.demo.hfrecyclerview.model.DemoConfigs
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DemoViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val configs = savedStateHandle.get<DemoConfigs>("configs") ?: DemoConfigs()

    val banners = MutableStateFlow(emptyList<Banner>())
    val photos = MutableStateFlow(emptyList<Photo>())

    private var pageNum = -1

    val pagingSource = object : PagingSource() {
        override fun doRefresh(dataVersion: Int) {
            viewModelScope.launch {
                val list = Banner.getListAsync(5)
                if (checkDataVersion(dataVersion)) {
                    banners.value = list
                }
            }
            viewModelScope.launch {
                val page = Photo.getPageAsync(pageSize = if (configs.notFullPage) 1 else 20)
                if (onRefreshSuccess(dataVersion, !page.hasMore)) {
                    photos.value = page.list
                    pageNum = 0
                }
            }
        }

        override fun doLoadMore(dataVersion: Int) {
            viewModelScope.launch {
                val page = Photo.getPageAsync(pageNum + 1, if (configs.notFullPage) 1 else 20)
                if (onLoadMoreSuccess(dataVersion, !page.hasMore)) {
                    photos.value += page.list
                    pageNum += 1
                }
            }
        }
    }

    init {
        pagingSource.refresh()
    }
}
