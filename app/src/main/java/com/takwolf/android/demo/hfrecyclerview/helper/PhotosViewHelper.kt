package com.takwolf.android.demo.hfrecyclerview.helper

import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.hfrecyclerview.adapter.PhotoListAdapter
import com.takwolf.android.demo.hfrecyclerview.holder.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.PagingPhotosViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotosViewModel

object PhotosViewHelper {
    fun <VH : PhotoListAdapter.ViewHolder> listen(
        owner: LifecycleOwner,
        viewModel: PhotosViewModel,
        adapter: PhotoListAdapter<VH>,
    ) {
        viewModel.photosData.observe(owner) { photos ->
            adapter.submitList(ArrayList(photos))
        }
        adapter.onPhotosSwapListener = { oldPosition, newPosition ->
            viewModel.swapPhotos(oldPosition, newPosition)
        }
        adapter.onPhotoDeleteListener = { position ->
            viewModel.deletePhoto(position)
        }
    }

    fun listenerPaging(
        owner: LifecycleOwner,
        viewModel: PagingPhotosViewModel,
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
