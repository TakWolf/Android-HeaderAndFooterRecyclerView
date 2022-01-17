package com.takwolf.android.demo.hfrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemGridVerticalBinding
import com.takwolf.android.hfrecyclerview.ProxyAdapter

class GridVerticalAdapter : PhotoListAdapter<GridVerticalAdapter.ViewHolder>() {
    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_FULL_SPAN = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 11 == 0) {
            TYPE_FULL_SPAN;
        } else {
            TYPE_NORMAL;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGridVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemGridVerticalBinding) : PhotoListAdapter.ViewHolder(binding.root) {
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

class GridVerticalSpanSizeLookup(
    private val layoutManager: GridLayoutManager,
    private val proxyAdapter: ProxyAdapter,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        val viewType = proxyAdapter.getItemViewType(position);
        if (viewType == ProxyAdapter.VIEW_TYPE_HEADER || viewType == ProxyAdapter.VIEW_TYPE_FOOTER || viewType == GridVerticalAdapter.TYPE_FULL_SPAN) {
            return layoutManager.spanCount
        } else {
            return 1
        }
    }
}
