package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.GridVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.widget.BannerPageHeader
import com.takwolf.android.demo.hfrecyclerview.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoPagingViewModel

class RefreshGridActivity : AppCompatActivity() {
    private val viewModel: PhotoPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.refresh_grid)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val bannerPageHeader = BannerPageHeader(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        val adapter = GridVerticalAdapter().apply {
            binding.recyclerView.adapter = this
        }
        viewModel.setupViews(this, binding.refreshLayout, loadMoreFooter, bannerPageHeader.adapter, adapter)
    }
}
