package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityViewPagerHeaderBinding
import com.takwolf.android.demo.hfrecyclerview.ui.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.ui.widget.BannerPageHeader
import com.takwolf.android.demo.hfrecyclerview.vm.BannerPageViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class ViewPagerHeaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewPagerHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bannerPageViewModel: BannerPageViewModel by viewModels()
        val photoListViewModel: PhotoListViewModel by viewModels()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val bannerPageHeader = BannerPageHeader(binding.recyclerView)
        bannerPageHeader.listen(this, bannerPageViewModel, photoListViewModel)
        val adapter = LinearVerticalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
