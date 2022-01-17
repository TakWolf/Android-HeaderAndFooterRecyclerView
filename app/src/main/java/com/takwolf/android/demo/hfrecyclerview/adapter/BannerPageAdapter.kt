package com.takwolf.android.demo.hfrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.data.ColorInfo
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemPageBinding

class BannerPageAdapter : ListAdapter<ColorInfo, BannerPageAdapter.ViewHolder>(ColorInfoDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, getItem(position))
    }

    class ViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, colorInfo: ColorInfo) {
            binding.tvText.text = position.toString()
            binding.tvText.setBackgroundColor(colorInfo.color)
        }
    }
}

object ColorInfoDiffItemCallback : DiffUtil.ItemCallback<ColorInfo>() {
    override fun areItemsTheSame(oldItem: ColorInfo, newItem: ColorInfo): Boolean {
        return oldItem.color == newItem.color
    }

    override fun areContentsTheSame(oldItem: ColorInfo, newItem: ColorInfo): Boolean {
        return oldItem == newItem
    }
}
