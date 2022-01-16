package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PagingPhotosViewModel : PhotosViewModel() {
    val refreshStateData = MutableLiveData(false)
    val loadMoreStateData = MutableLiveData(LoadMoreState.DISABLED)
    private var isRefreshDoing = false
    private var isLoadMoreDoing = false
    private var isLoadMoreFinished = false

    init {
        refresh()
    }

    fun refresh() {
        if (!isRefreshDoing) {
            isRefreshDoing = true
            refreshStateData.value = true
            viewModelScope.launch {
                delay(2000)
                photosData.value = Photo.getList(20)
                isRefreshDoing = false
                isLoadMoreDoing = false
                isLoadMoreFinished = false
                refreshStateData.value = false
                loadMoreStateData.value = LoadMoreState.ENDLESS
            }
        }
    }

    fun loadMore() {
        if (!isLoadMoreDoing && !isLoadMoreFinished) {
            isLoadMoreDoing = true
            loadMoreStateData.value = LoadMoreState.LOADING
            viewModelScope.launch {
                delay(2000)
                photosData.value?.let { photos ->
                    photos.addAll(Photo.getList(20))
                    if (photos.size >= 100) {
                        isLoadMoreFinished = true
                    }
                    photosData.value = photos
                }
                isLoadMoreDoing = false
                loadMoreStateData.value = if (isLoadMoreFinished) LoadMoreState.FINISHED else LoadMoreState.ENDLESS
            }
        }
    }
}
