package com.takwolf.android.demo.hfrecyclerview.ui.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.FooterLoadMoreHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.FooterLoadMoreVerticalBinding
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
import com.takwolf.android.hfrecyclerview.paging.LoadMoreState

class LoadMoreFooter private constructor(
    rootView: View,
    private val loadingBar: ProgressBar,
    private val tvText: TextView,
) : com.takwolf.android.hfrecyclerview.paging.LoadMoreFooter(rootView) {
    companion object {
        fun vertical(recyclerView: HeaderAndFooterRecyclerView): LoadMoreFooter {
            val binding = FooterLoadMoreVerticalBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.footerViewContainer, false)
            return LoadMoreFooter(binding.root, binding.loadingBar, binding.tvText)
        }

        fun horizontal(recyclerView: HeaderAndFooterRecyclerView): LoadMoreFooter {
            val binding = FooterLoadMoreHorizontalBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.footerViewContainer, false)
            return LoadMoreFooter(binding.root, binding.loadingBar, binding.tvText)
        }
    }

    init {
        tvText.setOnClickListener {
            checkDoLoadMore()
        }
        preloadOffset = 1
    }

    override fun onUpdateViews() {
        when (state) {
            LoadMoreState.DISABLED -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.INVISIBLE
                tvText.text = null
                tvText.isClickable = false
            }
            LoadMoreState.IDLE -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.VISIBLE
                tvText.text = null
                tvText.isClickable = true
            }
            LoadMoreState.LOADING -> {
                loadingBar.visibility = View.VISIBLE
                tvText.visibility = View.INVISIBLE
                tvText.text = null
                tvText.isClickable = false
            }
            LoadMoreState.FINISHED -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.VISIBLE
                tvText.setText(R.string.load_more_finished)
                tvText.isClickable = false
            }
            LoadMoreState.FAILED -> {
                loadingBar.visibility = View.INVISIBLE
                tvText.visibility = View.VISIBLE
                tvText.setText(R.string.load_more_failed)
                tvText.isClickable = true
            }
        }
    }
}
