package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemLinearHorizontalBinding

class LinearHorizontalAdapter : PhotoListAdapter<LinearHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLinearHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        binding: ItemLinearHorizontalBinding,
    ) : PhotoListAdapter.ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
}
