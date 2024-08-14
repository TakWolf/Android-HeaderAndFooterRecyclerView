package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.GridHorizontalAdapter
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class GridHorizontalActivity : AppCompatActivity() {
    private val viewModel: PhotoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.grid_horizontal)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.addHeaderView(R.layout.header_horizontal)
        binding.recyclerView.addFooterView(R.layout.footer_horizontal)
        val adapter = GridHorizontalAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.setupAdapter(this, adapter)
    }
}
