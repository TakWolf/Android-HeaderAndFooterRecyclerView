package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.hfrecyclerview.model.entity.Banner
import com.takwolf.android.demo.hfrecyclerview.model.entity.Photo
import com.takwolf.android.demo.hfrecyclerview.model.repository.BannerRepository
import com.takwolf.android.demo.hfrecyclerview.model.repository.PhotoRepository
import com.takwolf.android.demo.hfrecyclerview.util.PagingSource
import kotlinx.coroutines.launch

class PhotoPagingViewModel : ViewModel() {
    private val bannerRepository = BannerRepository()
    private val photoRepository = PhotoRepository()

    val banners = MutableLiveData(emptyList<Banner>())
    val photos = MutableLiveData(emptyList<Photo>())

    private var pageNum = -1

    val pagingSource = PagingSource(doRefresh = { dataVersion ->
        viewModelScope.launch {
            val list = bannerRepository.getList()
            if (checkDataVersion(dataVersion)) {
                banners.value = list
            }
        }
        viewModelScope.launch {
            val page = photoRepository.getPage()
            if (onRefreshSuccess(dataVersion, !page.hasMore)) {
                photos.value = page.list
                pageNum = 0
            }
        }
    }, doLoadMore = { dataVersion ->
        viewModelScope.launch {
            val page = photoRepository.getPage(pageNum + 1)
            if (onLoadMoreSuccess(dataVersion, !page.hasMore)) {
                photos.value = (photos.value ?: emptyList()) + page.list
                pageNum += 1
            }
        }
    })

    init {
        pagingSource.refresh()
    }
}
