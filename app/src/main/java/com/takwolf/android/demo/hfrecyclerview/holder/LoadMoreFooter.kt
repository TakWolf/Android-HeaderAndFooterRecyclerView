package com.takwolf.android.demo.hfrecyclerview.holder

import android.view.LayoutInflater
import android.view.View
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.FooterLoadMoreBinding
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
import com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreState

class LoadMoreFooter(val binding: FooterLoadMoreBinding) : com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter(binding.root) {
    companion object {
        fun create(recyclerView: HeaderAndFooterRecyclerView): LoadMoreFooter {
            val binding = FooterLoadMoreBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.footerViewContainer, false)
            return LoadMoreFooter(binding)
        }
    }

    init {
        binding.tvText.setOnClickListener {
            checkDoLoadMore()
        }
    }

    override fun onUpdateViews(footerView: View, @LoadMoreState state: Int) {
        when (state) {
            LoadMoreState.DISABLED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            LoadMoreState.LOADING -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            LoadMoreState.FINISHED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.loading_finished)
                binding.tvText.isClickable = false
            }
            LoadMoreState.ENDLESS -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = true
            }
            LoadMoreState.FAILED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.loading_failed_click_to_retry)
                binding.tvText.isClickable = true
            }
        }
    }
}
