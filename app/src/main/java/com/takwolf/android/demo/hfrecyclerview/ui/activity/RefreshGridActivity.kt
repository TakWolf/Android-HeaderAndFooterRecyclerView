package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.GridVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.OnPhotoDeleteListener
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.OnPhotosSwapListener
import com.takwolf.android.demo.hfrecyclerview.ui.widget.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.PagingViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.holder.setupView

class RefreshGridActivity : AppCompatActivity() {
    private val viewModel: PagingViewModel by viewModels()

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
        val loadMoreFooter = LoadMoreFooter.create(layoutInflater, binding.recyclerView)
        loadMoreFooter.addToRecyclerView(binding.recyclerView)
        val adapter = GridVerticalAdapter(layoutInflater).apply {
            onPhotosSwapListener = OnPhotosSwapListener(viewModel.photosHolder)
            onPhotoDeleteListener = OnPhotoDeleteListener(viewModel.photosHolder)
        }
        binding.recyclerView.adapter = adapter
        viewModel.photosHolder.setupView(this, binding.refreshLayout, loadMoreFooter, adapter)

        viewModel.toastHolder.setupView(this, this)
    }
}
