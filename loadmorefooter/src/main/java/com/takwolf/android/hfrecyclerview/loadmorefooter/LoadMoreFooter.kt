package com.takwolf.android.hfrecyclerview.loadmorefooter

import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

abstract class LoadMoreFooter(val view: View) {
    enum class State {
        DISABLED,
        IDLE,
        LOADING,
        FINISHED,
        FAILED,
    }

    var state = State.DISABLED
        set(value) {
            if (field != value) {
                field = value
                onUpdateViews()
            }
        }

    private var recyclerView: HeaderAndFooterRecyclerView? = null

    @IntRange(from = 0)
    var preloadOffset = 0

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            handleOnScrolled()
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            handleOnScrolled()
        }

        private fun handleOnScrolled() {
            recyclerView?.let { recyclerView ->
                if (!recyclerView.canScrollVertically(1)) {
                    checkDoLoadMore()
                } else if (preloadOffset > 0) {
                    recyclerView.layoutManager?.also { layoutManager ->
                        when (layoutManager) {
                            is LinearLayoutManager -> {
                                val lastPosition = layoutManager.findLastVisibleItemPosition()
                                if (lastPosition >= recyclerView.proxyAdapter.itemCount - preloadOffset) {
                                    checkDoLoadMore()
                                }
                            }
                            is StaggeredGridLayoutManager -> {
                                val lastPositions = layoutManager.findLastVisibleItemPositions(null)
                                for (lastPosition in lastPositions) {
                                    if (lastPosition >= recyclerView.proxyAdapter.itemCount - preloadOffset) {
                                        checkDoLoadMore()
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    var onLoadMoreListener: OnLoadMoreListener? = null

    fun addToRecyclerView(recyclerView: HeaderAndFooterRecyclerView) {
        if (this.recyclerView != null) {
            throw IllegalStateException("LoadMoreFooter has been added")
        }
        recyclerView.addFooterView(view)
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
        if (state == State.IDLE || state == State.FAILED) {
            state = State.LOADING
            onLoadMoreListener?.onLoadMore()
        }
    }

    protected abstract fun onUpdateViews()

    fun interface OnLoadMoreListener {
        fun onLoadMore()
    }
}
