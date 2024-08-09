package com.takwolf.android.demo.hfrecyclerview.ui.widget

import android.view.LayoutInflater
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderViewPagerBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.BannerPageAdapter
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class BannerPageHeader(
    recyclerView: HeaderAndFooterRecyclerView,
) {
    val binding = HeaderViewPagerBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.headerViewContainer, false)
    val adapter = BannerPageAdapter().apply {
        binding.viewPager.adapter = this
    }

    fun addToRecyclerView(recyclerView: HeaderAndFooterRecyclerView) {
        recyclerView.addHeaderView(binding.root)
    }
}
