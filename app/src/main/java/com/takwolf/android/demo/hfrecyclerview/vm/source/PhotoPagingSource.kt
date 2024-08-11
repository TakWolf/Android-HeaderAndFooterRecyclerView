package com.takwolf.android.demo.hfrecyclerview.vm.source

import com.takwolf.android.demo.hfrecyclerview.model.Banner
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoPagingSource(
    private val scope: CoroutineScope,
) : PagingSource() {
    val banners = MutableStateFlow(emptyList<Banner>())
    val photos = MutableStateFlow(emptyList<Photo>())

    private var pageNum = -1

    override fun doRefresh(dataVersion: Int) {
        scope.launch {
            val list = Banner.getListAsync(5)
            if (checkDataVersion(dataVersion)) {
                banners.value = list
            }
        }
        scope.launch {
            val page = Photo.getPageAsync()
            if (onRefreshSuccess(dataVersion, !page.hasMore)) {
                photos.value = page.list
                pageNum = 0
            }
        }
    }

    override fun doLoadMore(dataVersion: Int) {
        scope.launch {
            val page = Photo.getPageAsync(pageNum + 1)
            if (onLoadMoreSuccess(dataVersion, !page.hasMore)) {
                photos.value += page.list
                pageNum += 1
            }
        }
    }
}
