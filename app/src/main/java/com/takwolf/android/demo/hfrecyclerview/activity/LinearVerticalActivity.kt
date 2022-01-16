package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.LinearVerticalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.HfViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotosViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.HfViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.SinglePhotosViewModel

class LinearVerticalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hfViewModel: HfViewModel by viewModels()
        val photosViewModel: SinglePhotosViewModel by viewModels()

        binding.toolbar.setTitle(R.string.linear_vertical)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        HfViewHelper.setupVertical(hfViewModel, binding.recyclerView)
        HfViewHelper.listenVertical(hfViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = LinearVerticalAdapter()
        PhotosViewHelper.listen(this, photosViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
