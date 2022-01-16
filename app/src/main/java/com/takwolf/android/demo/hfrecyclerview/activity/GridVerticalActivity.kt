package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.GridVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.adapter.GridVerticalSpanSizeLookup
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.ExtraInfoViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraInfoListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class GridVerticalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraInfoListViewModel: ExtraInfoListViewModel by viewModels()
        val photoListViewModel: PhotoListViewModel by viewModels()

        binding.toolbar.setTitle(R.string.grid_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = GridVerticalSpanSizeLookup(layoutManager, binding.recyclerView.proxyAdapter)
        binding.recyclerView.layoutManager = layoutManager
        ExtraInfoViewHelper.setupVertical(extraInfoListViewModel, binding.recyclerView)
        ExtraInfoViewHelper.listenVertical(extraInfoListViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = GridVerticalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
