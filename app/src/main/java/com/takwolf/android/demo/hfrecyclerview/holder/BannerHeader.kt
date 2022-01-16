package com.takwolf.android.demo.hfrecyclerview.holder

import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.hfrecyclerview.adapter.BannerPageAdapter
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderViewPagerBinding
import com.takwolf.android.demo.hfrecyclerview.vm.BannerViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class BannerHeader(recyclerView: HeaderAndFooterRecyclerView) {
    val binding = HeaderViewPagerBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.headerViewContainer, false)
    val adapter = BannerPageAdapter()

    init {
        binding.viewPager.adapter = adapter
        recyclerView.addHeaderView(binding.root)
    }

    fun listen(owner: LifecycleOwner, bannerViewModel: BannerViewModel, photosViewModel: SinglePhotosViewModel) {
        bannerViewModel.infosData.observe(owner) { colorInfos ->
            adapter.submitList(colorInfos)
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                photosViewModel.photosData.value = Photo.getList()
            }
        })
    }
}
