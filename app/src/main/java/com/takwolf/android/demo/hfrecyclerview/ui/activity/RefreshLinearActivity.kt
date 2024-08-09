package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.widget.BannerPageHeader
import com.takwolf.android.demo.hfrecyclerview.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoPagingViewModel
import kotlinx.coroutines.launch

class RefreshLinearActivity : AppCompatActivity() {
    private val viewModel: PhotoPagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.refresh_linear)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val bannerPageHeader = BannerPageHeader(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView).apply {
            addToRecyclerView(binding.recyclerView)
        }
        viewModel.pagingSource.setupViews(this, binding.refreshLayout, loadMoreFooter)
        val adapter = LinearVerticalAdapter().apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos.value.toMutableList().let { photos ->
                    photos.removeAt(position)
                    viewModel.photos.value = photos
                }
            }

            binding.recyclerView.adapter = this
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.banners.collect { banners ->
                    bannerPageHeader.adapter.submitList(banners)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photos.collect { photos ->
                    adapter.submitList(photos)
                }
            }
        }
    }
}
