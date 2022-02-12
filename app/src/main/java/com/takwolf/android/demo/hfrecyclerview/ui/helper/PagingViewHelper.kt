package com.takwolf.android.demo.hfrecyclerview.ui.helper

import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.takwolf.android.demo.hfrecyclerview.vm.PagingViewModel
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter

object PagingViewHelper {
    fun <Entity> listen(
        owner: LifecycleOwner,
        viewModel: PagingViewModel<Entity, *>,
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
