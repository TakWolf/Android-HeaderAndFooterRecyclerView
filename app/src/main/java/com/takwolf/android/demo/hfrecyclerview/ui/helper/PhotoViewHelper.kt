package com.takwolf.android.demo.hfrecyclerview.ui.helper

import androidx.lifecycle.LifecycleOwner
import com.takwolf.android.demo.hfrecyclerview.ui.adapter.PhotoListAdapter
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.vm.ListViewModel
import java.util.*

object PhotoViewHelper {
    fun <VH : PhotoListAdapter.ViewHolder> listen(
        owner: LifecycleOwner,
        viewModel: ListViewModel<Photo>,
        adapter: PhotoListAdapter<VH>,
    ) {
        viewModel.entitiesData.observe(owner) { photos ->
            adapter.submitList(ArrayList(photos))
        }
        adapter.onPhotosSwapListener = { oldPosition, newPosition ->
            viewModel.entitiesData.value?.let { photos ->
                Collections.swap(photos, oldPosition, newPosition)
                viewModel.entitiesData.value = photos
            }
        }
        adapter.onPhotoDeleteListener = { position ->
            viewModel.entitiesData.value?.let { photos ->
                photos.removeAt(position)
                viewModel.entitiesData.value = photos
            }
        }
    }
}
