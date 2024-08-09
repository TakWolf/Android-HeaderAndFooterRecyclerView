package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearHorizontalAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel
import kotlinx.coroutines.launch

class LinearHorizontalActivity : AppCompatActivity() {
    private val viewModel: PhotoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.linear_horizontal)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.addHeaderView(R.layout.header_horizontal)
        binding.recyclerView.addFooterView(R.layout.footer_horizontal)
        val adapter = LinearHorizontalAdapter().apply {
            onPhotoDeleteListener = { position ->
                viewModel.photos.value.toMutableList().let { photos ->
                    photos.removeAt(position)
                    viewModel.photos.value = photos
                }
            }

            binding.recyclerView.adapter = this
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photos.collect { photos ->
                    adapter.submitList(photos)
                }
            }
        }
    }
}
