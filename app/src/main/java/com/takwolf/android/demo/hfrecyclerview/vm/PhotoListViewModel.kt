package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.PhotoListAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel : ViewModel() {
    private val photos = MutableStateFlow(Photo.newList(20))

    fun <VH : PhotoListAdapter.ViewHolder> setupViews(
        owner: LifecycleOwner,
        adapter: PhotoListAdapter<VH>,
    ) {
        adapter.onPhotoDeleteListener = { position ->
            photos.value.toMutableList().let { photos ->
                photos.removeAt(position)
                this@PhotoListViewModel.photos.value = photos
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photos.collect { photos ->
                    adapter.submitList(photos)
                }
            }
        }
    }
}
