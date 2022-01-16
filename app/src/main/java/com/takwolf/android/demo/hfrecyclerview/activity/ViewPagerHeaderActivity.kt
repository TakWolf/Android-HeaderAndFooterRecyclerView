package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityViewPagerHeaderBinding
import com.takwolf.android.demo.hfrecyclerview.helper.PhotosViewHelper
import com.takwolf.android.demo.hfrecyclerview.holder.BannerHeader
import com.takwolf.android.demo.hfrecyclerview.vm.BannerViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel

class ViewPagerHeaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewPagerHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bannerViewModel: BannerViewModel by viewModels()
        val photosViewModel: SinglePhotosViewModel by viewModels()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val bannerHeader = BannerHeader(binding.recyclerView)
        bannerHeader.listen(this, bannerViewModel, photosViewModel)
        val adapter = LinearVerticalAdapter()
        PhotosViewHelper.listen(this, photosViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
