package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.StaggeredHorizontalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.HfViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotosViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.HfViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel

class StaggeredHorizontalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hfViewModel: HfViewModel by viewModels()
        val photosViewModel: SinglePhotosViewModel by viewModels()

        binding.toolbar.setTitle(R.string.staggered_horizontal)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.HORIZONTAL)
        HfViewHelper.setupHorizontal(hfViewModel, binding.recyclerView)
        HfViewHelper.listenHorizontal(hfViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = StaggeredHorizontalAdapter()
        PhotosViewHelper.listen(this, photosViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
