package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityMultiRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.ui.helper.ExtraInfoViewHelper
import com.takwolf.android.demo.hfrecyclerview.ui.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraInfoListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class MultiRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMultiRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelProvider = ViewModelProvider(this)
        val extraInfoListViewModel1 = viewModelProvider["infos_1", ExtraInfoListViewModel::class.java]
        val extraInfoListViewModel2 = viewModelProvider["infos_2", ExtraInfoListViewModel::class.java]
        val photoListViewModel1 = viewModelProvider["photos_1", PhotoListViewModel::class.java]
        val photoListViewModel2 = viewModelProvider["photos_2", PhotoListViewModel::class.java]

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val recycledViewPool = RecyclerView.RecycledViewPool()

        binding.recyclerViewLeft.setRecycledViewPool(recycledViewPool)
        binding.recyclerViewLeft.layoutManager = LinearLayoutManager(this)
        ExtraInfoViewHelper.setupVertical(extraInfoListViewModel1, binding.recyclerViewLeft)
        val adapter1 = LinearVerticalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel1, adapter1)
        binding.recyclerViewLeft.adapter = adapter1

        binding.recyclerViewRight.setRecycledViewPool(recycledViewPool)
        binding.recyclerViewRight.layoutManager = LinearLayoutManager(this)
        ExtraInfoViewHelper.setupVertical(extraInfoListViewModel2, binding.recyclerViewRight)
        val adapter2 = LinearVerticalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel2, adapter2)
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
    }
}
