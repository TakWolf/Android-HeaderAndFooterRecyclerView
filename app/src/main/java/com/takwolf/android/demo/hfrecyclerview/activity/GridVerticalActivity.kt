package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.GridVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.adapter.GridVerticalSpanSizeLookup
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.HfViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotosViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.HfViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel

class GridVerticalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hfViewModel: HfViewModel by viewModels()
        val photosViewModel: SinglePhotosViewModel by viewModels()

        binding.toolbar.setTitle(R.string.grid_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = GridVerticalSpanSizeLookup(layoutManager, binding.recyclerView.proxyAdapter)
        binding.recyclerView.layoutManager = layoutManager
        HfViewHelper.setupVertical(hfViewModel, binding.recyclerView)
        HfViewHelper.listenVertical(hfViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = GridVerticalAdapter()
        PhotosViewHelper.listen(this, photosViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
