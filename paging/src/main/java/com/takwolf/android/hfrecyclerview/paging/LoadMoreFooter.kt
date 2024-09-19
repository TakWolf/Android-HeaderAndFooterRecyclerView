package com.takwolf.android.hfrecyclerview.paging

import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

abstract class LoadMoreFooter(val view: View) {
    private var recyclerView: HeaderAndFooterRecyclerView? = null

    var state = LoadMoreState.DISABLED
        set(value) {
            if (field != value) {
                field = value
                onUpdateViews()
            }
        }

    @IntRange(from = 0)
    var preloadOffset = 0

    var onLoadMoreListener: OnLoadMoreListener? = null

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            handleOnScrolled()
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            handleOnScrolled()
        }

        private fun handleOnScrolled() {
            recyclerView?.let { recyclerView ->
                recyclerView.layoutManager?.let { layoutManager ->
                    when (layoutManager) {
                        is LinearLayoutManager -> layoutManager.findLastCompletelyVisibleItemPosition()
                        is StaggeredGridLayoutManager -> layoutManager.findLastCompletelyVisibleItemPositions(null).max()
                        else -> null
                    }
                }?.let { lastPosition ->
                    if (lastPosition >= recyclerView.proxyAdapter.itemCount - 1 - preloadOffset) {
                        checkDoLoadMore()
                    }
                }
            }
        }
    }

    fun addToRecyclerView(recyclerView: HeaderAndFooterRecyclerView, index: Int? = null) {
        if (this.recyclerView != null) {
            throw IllegalStateException("LoadMoreFooter has been added")
        }
        index?.also {
            recyclerView.addFooterView(view, it)
        } ?: run {
            recyclerView.addFooterView(view)
        }
        recyclerView.addOnScrollListener(onScrollListener)
        this.recyclerView = recyclerView
        onUpdateViews()
    }

    fun removeFromRecyclerView() {
        recyclerView?.apply {
            removeOnScrollListener(onScrollListener)
            removeFooterView(view)
            recyclerView = null
        }
    }

    fun checkDoLoadMore() {
        if (state == LoadMoreState.IDLE || state == LoadMoreState.FAILED) {
            state = LoadMoreState.LOADING
            onLoadMoreListener?.onLoadMore()
        }
    }

    protected abstract fun onUpdateViews()

    fun interface OnLoadMoreListener {
        fun onLoadMore()
    }
}
