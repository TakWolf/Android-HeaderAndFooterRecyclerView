package com.takwolf.android.demo.hfrecyclerview.holder

import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.hfrecyclerview.adapter.BannerPageAdapter
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderViewPagerBinding
import com.takwolf.android.demo.hfrecyclerview.vm.BannerPageViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class BannerPageHeader(recyclerView: HeaderAndFooterRecyclerView) {
    val binding = HeaderViewPagerBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.headerViewContainer, false)
    val adapter = BannerPageAdapter()

    init {
        binding.viewPager.adapter = adapter
        recyclerView.addHeaderView(binding.root)
    }

    fun listen(owner: LifecycleOwner, bannerPageViewModel: BannerPageViewModel, photoListViewModel: PhotoListViewModel) {
        bannerPageViewModel.entitiesData.observe(owner) { colorInfos ->
            adapter.submitList(colorInfos)
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                photoListViewModel.entitiesData.value = Photo.getList()
            }
        })
    }
}
