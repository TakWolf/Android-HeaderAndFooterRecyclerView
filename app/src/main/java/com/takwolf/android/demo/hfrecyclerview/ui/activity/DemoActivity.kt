package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.HFRVHack.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityDemoBinding
import com.takwolf.android.demo.hfrecyclerview.model.DemoConfigs
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.widget.BannerPageHeader
import com.takwolf.android.demo.hfrecyclerview.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.DemoViewModel
import kotlinx.coroutines.launch

class DemoActivity : AppCompatActivity() {
    companion object {
        fun open(activity: AppCompatActivity, configs: DemoConfigs) {
            val intent = Intent(activity, DemoActivity::class.java).apply {
                configs.putToIntentExtra(this)
            }
            activity.startActivity(intent)
        }
    }

    private val viewModel by viewModels<DemoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val configs = DemoConfigs.getFromIntentExtra(intent)

        window.decorView.layoutDirection = if (configs.isRTL) {
            View.LAYOUT_DIRECTION_RTL
        } else {
            View.LAYOUT_DIRECTION_LTR
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        if (configs.enableRefresh && configs.orientation == RecyclerView.VERTICAL && !configs.reverseLayout) {
            binding.refreshLayout.isEnabled = true
            binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
            viewModel.pagingSource.setupSwipeRefreshLayout(this, binding.refreshLayout)
        } else {
            binding.refreshLayout.isEnabled = false
        }

        binding.recyclerView.layoutManager = when (configs.layoutManagerType) {
            DemoConfigs.LayoutManagerType.LINEAR -> {
                LinearLayoutManager(null, configs.orientation, configs.reverseLayout)
            }
            DemoConfigs.LayoutManagerType.GRID -> {
                GridLayoutManager(null, 3, configs.orientation, configs.reverseLayout)
            }
            DemoConfigs.LayoutManagerType.STAGGERED_GRID -> {
                StaggeredGridLayoutManager(3, configs.orientation).apply {
                    reverseLayout = configs.reverseLayout
                }
            }
        }

        if (configs.addBannerHeader) {
            val bannerPageHeader = if (configs.orientation == RecyclerView.VERTICAL) {
                BannerPageHeader.vertical(binding.recyclerView)
            } else {
                BannerPageHeader.horizontal(binding.recyclerView)
            }.apply {
                addToRecyclerView(binding.recyclerView)
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.banners.collect { banners ->
                        bannerPageHeader.adapter.submitList(banners)
                    }
                }
            }
        }

        if (configs.addStaticHeader) {
            if (configs.orientation == RecyclerView.VERTICAL) {
                binding.recyclerView.addHeaderView(R.layout.header_vertical)
            } else {
                binding.recyclerView.addHeaderView(R.layout.header_horizontal)
            }
        }

        if (configs.addStaticFooter) {
            if (configs.orientation == RecyclerView.VERTICAL) {
                binding.recyclerView.addFooterView(R.layout.footer_vertical)
            } else {
                binding.recyclerView.addFooterView(R.layout.footer_horizontal)
            }
        }

        if (configs.enableLoadMore) {
            val loadMoreFooter = if (configs.orientation == RecyclerView.VERTICAL) {
                LoadMoreFooter.vertical(binding.recyclerView)
            } else {
                LoadMoreFooter.horizontal(binding.recyclerView)
            }.apply {
                addToRecyclerView(binding.recyclerView)
            }
            viewModel.pagingSource.setupLoadMoreFooter(this, loadMoreFooter)
        }

        val adapter = when (configs.layoutManagerType) {
            DemoConfigs.LayoutManagerType.LINEAR -> {
                if (configs.orientation == RecyclerView.VERTICAL) {
                    PhotoListAdapter.linearVertical()
                } else {
                    PhotoListAdapter.linearHorizontal()
                }
            }
            DemoConfigs.LayoutManagerType.GRID -> {
                if (configs.orientation == RecyclerView.VERTICAL) {
                    PhotoListAdapter.gridVertical()
                } else {
                    PhotoListAdapter.gridHorizontal()
                }
            }
            DemoConfigs.LayoutManagerType.STAGGERED_GRID -> {
                if (configs.orientation == RecyclerView.VERTICAL) {
                    PhotoListAdapter.staggeredVertical()
                } else {
                    PhotoListAdapter.staggeredHorizontal()
                }
            }
        }.apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos.value.toMutableList().let { photos ->
                    photos.removeAt(position)
                    viewModel.photos.value = photos
                }
            }
        }
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photos.collect { photos ->
                    adapter.submitList(photos)
                }
            }
        }
    }
}