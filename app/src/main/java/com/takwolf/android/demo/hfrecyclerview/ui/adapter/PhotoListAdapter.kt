package com.takwolf.android.demo.hfrecyclerview.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ListLiveHolder
import java.util.*
import kotlin.math.abs
import kotlin.random.Random

abstract class PhotoListAdapter<VH : PhotoListAdapter.ViewHolder> : ListAdapter<Photo, VH>(PhotoDiffItemCallback) {
    var onPhotosSwapListener: OnPhotosSwapListener? = null
    var onPhotoDeleteListener: OnPhotoDeleteListener? = null

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected val onBtnItemClickListener = View.OnClickListener {
            (bindingAdapter as? PhotoListAdapter)?.let { adapter ->
                adapter.onPhotosSwapListener?.let { listener ->
                    val oldPosition = bindingAdapterPosition
                    val newPosition = abs(Random.nextInt() % adapter.itemCount)
                    listener.onPhotosSwap(oldPosition, newPosition)
                }
            }
        }

        protected val onBtnItemLongClickListener = View.OnLongClickListener {
            (bindingAdapter as? PhotoListAdapter)?.let { adapter ->
                adapter.onPhotoDeleteListener?.let { listener ->
                    val position = bindingAdapterPosition
                    listener.onPhotoDelete(position)
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

class OnPhotosSwapListener(private val listHolder: ListLiveHolder<Photo>) {
    fun onPhotosSwap(oldPosition: Int, newPosition: Int) {
        listHolder.entitiesData.value?.let { photos ->
            Collections.swap(photos, oldPosition, newPosition)
            listHolder.entitiesData.value = photos
        }
    }
}

class OnPhotoDeleteListener(private val listHolder: ListLiveHolder<Photo>) {
    fun onPhotoDelete(position: Int) {
        listHolder.entitiesData.value?.let { photos ->
            photos.removeAt(position)
            listHolder.entitiesData.value = photos
        }
    }
}
