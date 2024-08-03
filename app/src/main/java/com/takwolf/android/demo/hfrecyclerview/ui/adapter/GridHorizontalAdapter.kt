package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemGridHorizontalBinding

class GridHorizontalAdapter(
    private val layoutInflater: LayoutInflater,
) : PhotoListAdapter<GridHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGridHorizontalBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(binding: ItemGridHorizontalBinding) : PhotoListAdapter.ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
}
