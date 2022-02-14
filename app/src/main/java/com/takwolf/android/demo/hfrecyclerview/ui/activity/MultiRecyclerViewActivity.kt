package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityMultiRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.OnPhotoDeleteListener
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.OnPhotosSwapListener
import com.takwolf.android.demo.hfrecyclerview.vm.MultiListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.holder.setupView

class MultiRecyclerViewActivity : AppCompatActivity() {
    private val viewModel: MultiListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMultiRecyclerViewBinding.inflate(layoutInflater)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val recycledViewPool = RecyclerView.RecycledViewPool()

        binding.recyclerViewLeft.setRecycledViewPool(recycledViewPool)
        binding.recyclerViewLeft.layoutManager = LinearLayoutManager(this)
        viewModel.extraHolder1.setupVertical(binding.recyclerViewLeft)
        val adapter1 = LinearVerticalAdapter()
        adapter1.onPhotosSwapListener = OnPhotosSwapListener(viewModel.photosHolder1)
        adapter1.onPhotoDeleteListener = OnPhotoDeleteListener(viewModel.photosHolder1)
        viewModel.photosHolder1.setupView(this, adapter1)
        binding.recyclerViewLeft.adapter = adapter1

        binding.recyclerViewRight.setRecycledViewPool(recycledViewPool)
        binding.recyclerViewRight.layoutManager = LinearLayoutManager(this)
        viewModel.extraHolder2.setupVertical(binding.recyclerViewRight)
        val adapter2 = LinearVerticalAdapter()
        adapter2.onPhotosSwapListener = OnPhotosSwapListener(viewModel.photosHolder2)
        adapter2.onPhotoDeleteListener = OnPhotoDeleteListener(viewModel.photosHolder2)
        viewModel.photosHolder2.setupView(this, adapter2)
        binding.recyclerViewRight.adapter = adapter2

        binding.btnReplaceAdapters.setOnClickListener {
            val adapter = binding.recyclerViewLeft.adapter
            binding.recyclerViewLeft.adapter = binding.recyclerViewRight.adapter
            binding.recyclerViewRight.adapter = adapter
        }

        binding.btnSwapAdapters.setOnClickListener {
            val adapter = binding.recyclerViewLeft.adapter
            binding.recyclerViewLeft.swapAdapter(binding.recyclerViewRight.adapter, false)
            binding.recyclerViewRight.swapAdapter(adapter, false)
        }

        setContentView(binding.root)
    }
}
