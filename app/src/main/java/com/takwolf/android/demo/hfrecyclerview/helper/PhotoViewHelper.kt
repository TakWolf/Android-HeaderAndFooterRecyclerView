package com.takwolf.android.demo.hfrecyclerview.helper

import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.hfrecyclerview.adapter.PhotoListAdapter
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import com.takwolf.android.demo.hfrecyclerview.holder.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.ListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoPagingViewModel
import java.util.*
import kotlin.collections.ArrayList

object PhotoViewHelper {
    fun <VH : PhotoListAdapter.ViewHolder> listen(
        owner: LifecycleOwner,
        viewModel: ListViewModel<Photo>,
        adapter: PhotoListAdapter<VH>,
    ) {
        viewModel.entitiesData.observe(owner) { photos ->
            adapter.submitList(ArrayList(photos))
        }
        adapter.onPhotosSwapListener = { oldPosition, newPosition ->
            viewModel.entitiesData.value?.let { photos ->
                Collections.swap(photos, oldPosition, newPosition)
                viewModel.entitiesData.value = photos
            }
        }
        adapter.onPhotoDeleteListener = { position ->
            viewModel.entitiesData.value?.let { photos ->
                photos.removeAt(position)
                viewModel.entitiesData.value = photos
            }
        }
    }

    fun listenerPaging(
        owner: LifecycleOwner,
        viewModel: PhotoPagingViewModel,
        refreshLayout: SwipeRefreshLayout,
        loadMoreFooter: LoadMoreFooter,
    ) {
        viewModel.refreshStateData.observe(owner) {
            it?.let { isRefreshing ->
                refreshLayout.isRefreshing = isRefreshing
            }
        }
        viewModel.loadMoreStateData.observe(owner) {
            it?.let { state ->
                loadMoreFooter.state = state
            }
        }
        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        loadMoreFooter.setOnLoadMoreListener {
            viewModel.loadMore()
        }
    }
}
