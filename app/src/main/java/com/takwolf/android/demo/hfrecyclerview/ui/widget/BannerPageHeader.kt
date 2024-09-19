package com.takwolf.android.demo.hfrecyclerview.ui.widget

import android.view.LayoutInflater
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderViewPagerHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderViewPagerVerticalBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.BannerPageAdapter
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class BannerPageHeader private constructor(
    private val rootView: View,
    private val viewPager: ViewPager2,
) {
    companion object {
        fun vertical(recyclerView: HeaderAndFooterRecyclerView): BannerPageHeader {
            val binding = HeaderViewPagerVerticalBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.headerViewContainer, false)
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            return BannerPageHeader(binding.root, binding.viewPager)
        }

        fun horizontal(recyclerView: HeaderAndFooterRecyclerView): BannerPageHeader {
            val binding = HeaderViewPagerHorizontalBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.headerViewContainer, false)
            binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            return BannerPageHeader(binding.root, binding.viewPager)
        }
    }

    val adapter = BannerPageAdapter().apply {
        viewPager.adapter = this
    }

    fun addToRecyclerView(recyclerView: HeaderAndFooterRecyclerView, index: Int? = null) {
        index?.also {
            recyclerView.addHeaderView(rootView, it)
        } ?: run {
            recyclerView.addHeaderView(rootView)
        }
    }
}
