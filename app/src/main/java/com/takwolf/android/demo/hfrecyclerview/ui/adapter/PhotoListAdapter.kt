package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemGridHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemGridVerticalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemLinearHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemLinearVerticalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemStaggeredHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.ItemStaggeredVerticalBinding
import com.takwolf.android.demo.hfrecyclerview.model.Photo

abstract class PhotoListAdapter : ListAdapter<Photo, PhotoListAdapter.ViewHolder>(PhotoDiffItemCallback) {
    companion object {
        fun linearVertical(): PhotoListAdapter {
            return object : PhotoListAdapter() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val binding = ItemLinearVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
                }
            }
        }

        fun linearHorizontal(): PhotoListAdapter {
            return object : PhotoListAdapter() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val binding = ItemLinearHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
                }
            }
        }

        fun gridVertical(): PhotoListAdapter {
            return object : PhotoListAdapter() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val binding = ItemGridVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
                }
            }
        }

        fun gridHorizontal(): PhotoListAdapter {
            return object : PhotoListAdapter() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val binding = ItemGridHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
                }
            }
        }

        fun staggeredVertical(): PhotoListAdapter {
            return object : PhotoListAdapter() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val binding = ItemStaggeredVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
                }
            }
        }

        fun staggeredHorizontal(): PhotoListAdapter {
            return object : PhotoListAdapter() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val binding = ItemStaggeredHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHolder(binding.root, binding.btnItem, binding.imgPhoto)
                }
            }
        }
    }

    var onPhotoDeleteListener: ((position: Int) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemView: View,
        btnItem: View,
        private val imgPhoto: ImageView,
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            btnItem.setOnLongClickListener {
                (bindingAdapter as? PhotoListAdapter)?.let { adapter ->
                    adapter.onPhotoDeleteListener?.invoke(bindingAdapterPosition)
                }
                true
            }
        }

        internal fun bind(photo: Photo) {
            imgPhoto.load(photo.url) {
                placeholder(R.color.image_placeholder)
                error(R.color.image_placeholder)
            }
        }
    }
}

private object PhotoDiffItemCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}
