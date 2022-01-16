package com.takwolf.android.demo.hfrecyclerview.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.adapter.LinearHorizontalAdapter
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityRecyclerViewBinding
import com.takwolf.android.demo.hfrecyclerview.helper.ExtraInfoViewHelper
import com.takwolf.android.demo.hfrecyclerview.helper.PhotoViewHelper
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraInfoListViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.PhotoListViewModel

class LinearHorizontalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraInfoListViewModel: ExtraInfoListViewModel by viewModels()
        val photoListViewModel: PhotoListViewModel by viewModels()

        binding.toolbar.setTitle(R.string.linear_horizontal)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        ExtraInfoViewHelper.setupHorizontal(extraInfoListViewModel, binding.recyclerView)
        ExtraInfoViewHelper.listenHorizontal(extraInfoListViewModel, binding.recyclerView, binding.hfDashboard)
        val adapter = LinearHorizontalAdapter()
        PhotoViewHelper.listen(this, photoListViewModel, adapter)
        binding.recyclerView.adapter = adapter
    }
}
