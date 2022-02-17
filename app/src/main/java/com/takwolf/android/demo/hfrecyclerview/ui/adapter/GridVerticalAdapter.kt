package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemGridVerticalBinding
import com.takwolf.android.hfrecyclerview.ProxyAdapter

class GridVerticalAdapter(private val layoutInflater: LayoutInflater) : PhotoListAdapter<GridVerticalAdapter.ViewHolder>() {
    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_FULL_SPAN = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 11 == 0) {
            TYPE_FULL_SPAN
        } else {
            TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGridVerticalBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(binding: ItemGridVerticalBinding) : PhotoListAdapter.ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
}

class GridVerticalSpanSizeLookup(
    private val layoutManager: GridLayoutManager,
    private val proxyAdapter: ProxyAdapter,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        val viewType = proxyAdapter.getItemViewType(position)
        return if (viewType == ProxyAdapter.VIEW_TYPE_HEADER || viewType == ProxyAdapter.VIEW_TYPE_FOOTER || viewType == GridVerticalAdapter.TYPE_FULL_SPAN) {
            layoutManager.spanCount
        } else {
            1
        }
    }
}
