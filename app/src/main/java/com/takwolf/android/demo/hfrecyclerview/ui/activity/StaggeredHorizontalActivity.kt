package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.StaggeredHorizontalAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class StaggeredHorizontalActivity : AppCompatActivity() {
    private val viewModel: PhotoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.staggered_horizontal)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.HORIZONTAL)
        binding.recyclerView.addHeaderView(R.layout.header_horizontal)
        binding.recyclerView.addFooterView(R.layout.footer_horizontal)
        val adapter = StaggeredHorizontalAdapter().apply {
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
