package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.model.Photo

abstract class PhotoListAdapter<VH : PhotoListAdapter.ViewHolder> : ListAdapter<Photo, VH>(PhotoDiffItemCallback) {
    var onPhotoDeleteListener: ((position: Int) -> Unit)? = null

    abstract class ViewHolder(
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
