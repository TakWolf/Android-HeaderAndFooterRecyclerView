package com.takwolf.android.demo.hfrecyclerview.vm

import com.takwolf.android.demo.hfrecyclerview.data.Photo
import kotlinx.coroutines.delay

class PhotoPagingViewModel : PagingViewModel<Photo>() {
    init {
        refresh()
    }

    override suspend fun doRefresh() {
        delay(1000L)
        refreshSuccess(Photo.getList(20), false)
    }

    override suspend fun doLoadMore(version: Int) {
        delay(1000L)
        val isFinished = (entitiesData.value?.size ?: 0 + 20) >= 500
        loadMoreSuccess(version, Photo.getList(20), isFinished)
    }
}
