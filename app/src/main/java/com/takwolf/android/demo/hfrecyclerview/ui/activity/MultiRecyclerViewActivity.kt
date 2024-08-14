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
        val adapter1 = LinearVerticalAdapter()
        binding.recyclerViewLeft.adapter = adapter1

        binding.recyclerViewRight.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRight.addHeaderView(R.layout.header_vertical)
        binding.recyclerViewRight.addFooterView(R.layout.footer_vertical)
        val adapter2 = LinearVerticalAdapter()
        binding.recyclerViewRight.adapter = adapter2

        viewModel.setupAdapters(this, adapter1, adapter2)

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
