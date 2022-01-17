package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.StaggeredVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRefreshAndLoadMoreBinding
import com.takwolf.android.demo.hfrecyclerview.helper.PagingViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.holder.LoadMoreFooter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoPagingViewModel

class RefreshStaggeredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRefreshAndLoadMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoPagingViewModel: PhotoPagingViewModel by viewModels()

        binding.toolbar.setTitle(R.string.refresh_staggered)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.refreshLayout.setColorSchemeResources(R.color.app_primary)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        val loadMoreFooter = LoadMoreFooter.create(binding.recyclerView)
        PagingViewHelper.listen(this, photoPagingViewModel, binding.refreshLayout, loadMoreFooter)
        loadMoreFooter.addToRecyclerView(binding.recyclerView)
        val adapter = StaggeredVerticalAdapter()
        PhotoViewHelper.listen(this, photoPagingViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
