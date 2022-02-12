package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.StaggeredVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.helper.ExtraInfoViewHelper
import com.takwolf.android.demo.hfrecyclerview.ui.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraInfoListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class StaggeredVerticalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraInfoListViewModel: ExtraInfoListViewModel by viewModels()
        val photoListViewModel: PhotoListViewModel by viewModels()

        binding.toolbar.setTitle(R.string.staggered_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        ExtraInfoViewHelper.setupVertical(extraInfoListViewModel, binding.recyclerView)
        ExtraInfoViewHelper.listenVertical(extraInfoListViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = StaggeredVerticalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
