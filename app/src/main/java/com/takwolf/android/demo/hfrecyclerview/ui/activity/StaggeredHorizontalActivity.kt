package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.StaggeredHorizontalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.helper.ExtraInfoViewHelper
import com.takwolf.android.demo.hfrecyclerview.ui.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraInfoListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class StaggeredHorizontalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraInfoListViewModel: ExtraInfoListViewModel by viewModels()
        val photoListViewModel: PhotoListViewModel by viewModels()

        binding.toolbar.setTitle(R.string.staggered_horizontal)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.HORIZONTAL)
        ExtraInfoViewHelper.setupHorizontal(extraInfoListViewModel, binding.recyclerView)
        ExtraInfoViewHelper.listenHorizontal(extraInfoListViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = StaggeredHorizontalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
