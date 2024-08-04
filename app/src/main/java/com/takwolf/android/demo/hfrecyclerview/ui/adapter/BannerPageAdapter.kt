package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemPageBinding
import com.takwolf.android.demo.hfrecyclerview.model.Banner

class BannerPageAdapter(
    private val layoutInflater: LayoutInflater,
) : ListAdapter<Banner, BannerPageAdapter.ViewHolder>(ColorInfoDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, getItem(position))
    }

    class ViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, banner: Banner) {
            binding.tvText.text = position.toString()
            binding.tvText.setBackgroundColor(banner.color)
        }
    }
}

private object ColorInfoDiffItemCallback : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.color == newItem.color
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }
}
