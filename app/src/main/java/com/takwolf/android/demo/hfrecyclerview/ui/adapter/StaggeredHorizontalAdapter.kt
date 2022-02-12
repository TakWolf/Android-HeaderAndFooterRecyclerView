package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemStaggeredHorizontalBinding

class StaggeredHorizontalAdapter : PhotoListAdapter<StaggeredHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStaggeredHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemStaggeredHorizontalBinding) : PhotoListAdapter.ViewHolder(binding.root) {
        init {
            binding.btnItem.setOnClickListener(onBtnItemClickListener)
            binding.btnItem.setOnLongClickListener(onBtnItemLongClickListener)
        }

        fun bind(photo: Photo) {
            binding.imgPhoto.load(photo.url) {
                placeholder(R.color.image_placeholder)
            }
        }
    }
}
