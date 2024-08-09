package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.StaggeredVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.widget.BannerPageHeader
import com.takwolf.android.demo.hfrecyclerview.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoPagingViewModel

class RefreshStaggeredActivity : AppCompatActivity() {
    private val viewModel: PhotoPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.refresh_staggered)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        val bannerPageHeader = BannerPageHeader(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        viewModel.pagingSource.setupViews(this, binding.refreshLayout, loadMoreFooter)
        val adapter = StaggeredVerticalAdapter().apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos.value?.toMutableList()?.let { photos ->
                    photos.removeAt(position)
                    viewModel.photos.value = photos
                }
            }

            binding.recyclerView.adapter = this
        }
        viewModel.banners.observe(this) { banners ->
            bannerPageHeader.adapter.submitList(banners)
        }
        viewModel.photos.observe(this) { photos ->
            adapter.submitList(photos)
        }
    }
}
