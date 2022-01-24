package com.takwolf.android.demo.hfrecyclerview.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import kotlin.math.abs
import kotlin.random.Random

abstract class PhotoListAdapter<VH : PhotoListAdapter.ViewHolder> : ListAdapter<Photo, VH>(PhotoDiffItemCallback) {
    var onPhotosSwapListener: ((oldPosition: Int, newPosition: Int) -> Unit)? = null
    var onPhotoDeleteListener: ((position: Int) -> Unit)? = null

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected val onBtnItemClickListener = View.OnClickListener {
            (bindingAdapter as PhotoListAdapter?)?.let { adapter ->
                adapter.onPhotosSwapListener?.let { listener ->
                    val oldPosition = bindingAdapterPosition
                    val newPosition = abs(Random.nextInt() % adapter.itemCount)
                    listener(oldPosition, newPosition)
                }
            }
        }

        protected val onBtnItemLongClickListener = View.OnLongClickListener {
            (bindingAdapter as PhotoListAdapter?)?.let { adapter ->
                adapter.onPhotoDeleteListener?.let { listener ->
                    val position = bindingAdapterPosition
                    listener(position)
                }
            }
            true
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
