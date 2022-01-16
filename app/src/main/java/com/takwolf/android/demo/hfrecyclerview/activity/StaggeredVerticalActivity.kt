package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.StaggeredVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.HfViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotosViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.HfViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel

class StaggeredVerticalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hfViewModel: HfViewModel by viewModels()
        val photosViewModel: SinglePhotosViewModel by viewModels()

        binding.toolbar.setTitle(R.string.staggered_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        HfViewHelper.setupVertical(hfViewModel, binding.recyclerView)
        HfViewHelper.listenVertical(hfViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = StaggeredVerticalAdapter()
        PhotosViewHelper.listen(this, photosViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
