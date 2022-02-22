package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.OnPhotoDeleteListener
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.OnPhotosSwapListener
import com.takwolf.android.demo.hfrecyclerview.vm.SingleListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.holder.setupView

class LinearVerticalActivity : AppCompatActivity() {
    private val viewModel: SingleListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)

        binding.toolbar.setTitle(R.string.linear_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.extraHolder.setupVertical(layoutInflater, binding.recyclerView, binding.hfDashboard)
        val adapter = LinearVerticalAdapter(layoutInflater).apply {
            onPhotosSwapListener = OnPhotosSwapListener(viewModel.photosHolder)
            onPhotoDeleteListener = OnPhotoDeleteListener(viewModel.photosHolder)
        }
        viewModel.photosHolder.setupView(this, adapter)
        binding.recyclerView.adapter = adapter

        setContentView(binding.root)
    }
}
