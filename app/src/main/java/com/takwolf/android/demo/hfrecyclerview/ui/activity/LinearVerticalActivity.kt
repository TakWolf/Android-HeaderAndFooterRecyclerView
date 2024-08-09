package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel
import kotlinx.coroutines.launch

class LinearVerticalActivity : AppCompatActivity() {
    private val viewModel: PhotoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.linear_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addHeaderView(R.layout.header_vertical)
        binding.recyclerView.addFooterView(R.layout.footer_vertical)
        val adapter = LinearVerticalAdapter().apply {
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
