package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.BannerPageAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.source.PhotoPagingSource
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter
import kotlinx.coroutines.launch

class PhotoPagingViewModel : ViewModel() {
    private val pagingSource = PhotoPagingSource(viewModelScope)

    init {
        pagingSource.refresh()
    }

    fun <VH : PhotoListAdapter.ViewHolder> setupViews(
        owner: LifecycleOwner,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
        bannerPageAdapter: BannerPageAdapter,
        photoListAdapter: PhotoListAdapter<VH>,
    ) {
        pagingSource.setupViews(owner, refreshLayout, loadMoreFooter)
        photoListAdapter.onPhotoDeleteListener = { position ->
            pagingSource.photos.value.toMutableList().let { photos ->
                photos.removeAt(position)
                pagingSource.photos.value = photos
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingSource.banners.collect { banners ->
                    bannerPageAdapter.submitList(banners)
                }
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingSource.photos.collect { photos ->
                    photoListAdapter.submitList(photos)
                }
            }
        }
    }
}
