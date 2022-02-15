package com.takwolf.android.demo.hfrecyclerview.ui.widget

import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderViewPagerBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.BannerPageAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.holder.setupView
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class BannerPageHeader(recyclerView: HeaderAndFooterRecyclerView) {
    val binding = HeaderViewPagerBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.headerViewContainer, false)
    val adapter = BannerPageAdapter()

    init {
        binding.viewPager.adapter = adapter
        recyclerView.addHeaderView(binding.root)
    }

    fun setup(owner: LifecycleOwner, viewModel: ExtraListViewModel) {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.loadListAt(position)
            }
        })
        viewModel.bannersHolder.setupView(owner, adapter)
    }
}
