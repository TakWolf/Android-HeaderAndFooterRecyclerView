package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityMultiRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.HfViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotosViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.HfViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel

class MultiRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMultiRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelProvider = ViewModelProvider(this)
        val hfViewModel1 = viewModelProvider["hf_1", HfViewModel::class.java]
        val hfViewModel2 = viewModelProvider["hf_2", HfViewModel::class.java]
        val photosViewModel1 = viewModelProvider["photos_1", SinglePhotosViewModel::class.java]
        val photosViewModel2 = viewModelProvider["photos_2", SinglePhotosViewModel::class.java]

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val recycledViewPool = RecyclerView.RecycledViewPool()

        binding.recyclerViewLeft.setRecycledViewPool(recycledViewPool)
        binding.recyclerViewLeft.layoutManager = LinearLayoutManager(this)
        HfViewHelper.setupVertical(hfViewModel1, binding.recyclerViewLeft)
        val adapter1 = LinearVerticalAdapter()
        PhotosViewHelper.listen(this, photosViewModel1, adapter1)
        binding.recyclerViewLeft.adapter = adapter1

        binding.recyclerViewRight.setRecycledViewPool(recycledViewPool)
        binding.recyclerViewRight.layoutManager = LinearLayoutManager(this)
        HfViewHelper.setupVertical(hfViewModel2, binding.recyclerViewRight)
        val adapter2 = LinearVerticalAdapter()
        PhotosViewHelper.listen(this, photosViewModel2, adapter2)
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
