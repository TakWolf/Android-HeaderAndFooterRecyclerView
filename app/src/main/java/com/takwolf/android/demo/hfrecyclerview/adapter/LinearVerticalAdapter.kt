package com.takwolf.android.demo.hfrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemLinearVerticalBinding

class LinearVerticalAdapter : PhotoListAdapter<LinearVerticalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLinearVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemLinearVerticalBinding) : PhotoListAdapter.ViewHolder(binding.root) {
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
