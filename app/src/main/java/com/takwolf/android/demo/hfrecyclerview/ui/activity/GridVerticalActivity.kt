package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.GridVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class GridVerticalActivity : AppCompatActivity() {
    private val viewModel: PhotoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.grid_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.addHeaderView(R.layout.header_vertical)
        binding.recyclerView.addFooterView(R.layout.footer_vertical)
        val adapter = GridVerticalAdapter().apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos.value?.toMutableList()?.let { photos ->
                    photos.removeAt(position)
                    viewModel.photos.value = photos
                }
            }

            binding.recyclerView.adapter = this
        }
        viewModel.photos.observe(this) { photos ->
            adapter.submitList(photos)
        }
    }
}
