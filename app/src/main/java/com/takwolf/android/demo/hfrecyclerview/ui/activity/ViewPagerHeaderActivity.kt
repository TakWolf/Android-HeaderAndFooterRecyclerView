package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityViewPagerHeaderBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.widget.BannerPageHeader
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.holder.setupView

class ViewPagerHeaderActivity : AppCompatActivity() {
    private val viewModel: ExtraListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewPagerHeaderBinding.inflate(layoutInflater)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val bannerPageHeader = BannerPageHeader(layoutInflater, binding.recyclerView)
        bannerPageHeader.setup(this, viewModel)
        val adapter = LinearVerticalAdapter(layoutInflater)
        viewModel.photosHolder.setupView(this, adapter)
        binding.recyclerView.adapter = adapter

        setContentView(binding.root)
    }
}
