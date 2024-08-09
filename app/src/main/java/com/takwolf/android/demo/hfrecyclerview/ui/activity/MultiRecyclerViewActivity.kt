package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityMultiRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.MultiPhotoListViewModel

class MultiRecyclerViewActivity : AppCompatActivity() {
    private val viewModel: MultiPhotoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMultiRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerViewLeft.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLeft.addHeaderView(R.layout.header_vertical)
        binding.recyclerViewLeft.addFooterView(R.layout.footer_vertical)
        val adapter1 = LinearVerticalAdapter().apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos1.value?.toMutableList()?.let { photos ->
                    photos.removeAt(position)
                    viewModel.photos1.value = photos
                }
            }

            binding.recyclerViewLeft.adapter = this
        }
        viewModel.photos1.observe(this) { photos ->
            adapter1.submitList(photos)
        }

        binding.recyclerViewRight.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRight.addHeaderView(R.layout.header_vertical)
        binding.recyclerViewRight.addFooterView(R.layout.footer_vertical)
        val adapter2 = LinearVerticalAdapter().apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos2.value?.toMutableList()?.let { photos ->
                    photos.removeAt(position)
                    viewModel.photos2.value = photos
                }
            }

            binding.recyclerViewRight.adapter = this
        }
        viewModel.photos2.observe(this) { photos ->
            adapter2.submitList(photos)
        }

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
    }
}
