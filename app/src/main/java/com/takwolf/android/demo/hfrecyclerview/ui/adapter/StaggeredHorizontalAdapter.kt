package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemStaggeredHorizontalBinding

class StaggeredHorizontalAdapter(private val layoutInflater: LayoutInflater) : PhotoListAdapter<StaggeredHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStaggeredHorizontalBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(binding: ItemStaggeredHorizontalBinding) : PhotoListAdapter.ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
}
