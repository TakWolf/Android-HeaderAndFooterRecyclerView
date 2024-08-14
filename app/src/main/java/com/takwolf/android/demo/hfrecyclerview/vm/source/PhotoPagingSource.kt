package com.takwolf.android.demo.hfrecyclerview.vm.source

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.takwolf.android.demo.hfrecyclerview.model.Banner
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.BannerPageAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.PhotoListAdapter
import com.takwolf.android.hfrecyclerview.paging.PagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoPagingSource(
    private val scope: CoroutineScope,
) : PagingSource() {
    private val banners = MutableStateFlow(emptyList<Banner>())
    private val photos = MutableStateFlow(emptyList<Photo>())

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

    fun <VH : PhotoListAdapter.ViewHolder> setupAdapters(
        owner: LifecycleOwner,
        bannerPageAdapter: BannerPageAdapter,
        photoListAdapter: PhotoListAdapter<VH>,
    ) {
        photoListAdapter.onPhotoDeleteListener = { position ->
            photos.value.toMutableList().let { photos ->
                photos.removeAt(position)
                this@PhotoPagingSource.photos.value = photos
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                banners.collect { banners ->
                    bannerPageAdapter.submitList(banners)
                }
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photos.collect { photos ->
                    photoListAdapter.submitList(photos)
                }
            }
        }
    }
}
