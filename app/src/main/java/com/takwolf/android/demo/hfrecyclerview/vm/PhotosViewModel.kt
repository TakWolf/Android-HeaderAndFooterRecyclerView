package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.data.Photo
import java.util.*

abstract class PhotosViewModel : ViewModel() {
    val photosData: MutableLiveData<MutableList<Photo>> = MutableLiveData()

    fun swapPhotos(oldPosition: Int, newPosition: Int) {
        photosData.value?.let { photos ->
            Collections.swap(photos, oldPosition, newPosition)
            photosData.value = photos
        }
    }

    fun deletePhoto(position: Int) {
        photosData.value?.let { photos ->
            photos.removeAt(position)
            photosData.value = photos
        }
    }
}
