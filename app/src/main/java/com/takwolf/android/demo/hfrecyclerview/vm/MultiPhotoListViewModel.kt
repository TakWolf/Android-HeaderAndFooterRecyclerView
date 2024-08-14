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

class MultiPhotoListViewModel : ViewModel() {
    private val photos1 = MutableStateFlow(Photo.newList(20))
    private val photos2 = MutableStateFlow(Photo.newList(20))

    fun <VH : PhotoListAdapter.ViewHolder> setupAdapters(
        owner: LifecycleOwner,
        adapter1: PhotoListAdapter<VH>,
        adapter2: PhotoListAdapter<VH>,
    ) {
        adapter1.onPhotoDeleteListener = { position ->
            photos1.value.toMutableList().let { photos ->
                photos.removeAt(position)
                photos1.value = photos
            }
        }
        adapter2.onPhotoDeleteListener = { position ->
            photos2.value.toMutableList().let { photos ->
                photos.removeAt(position)
                photos2.value = photos
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photos1.collect { photos ->
                    adapter1.submitList(photos)
                }
            }
        }
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photos2.collect { photos ->
                    adapter2.submitList(photos)
                }
            }
        }
    }
}
