package com.takwolf.android.demo.hfrecyclerview.vm

import com.takwolf.android.demo.hfrecyclerview.data.Photo

class SinglePhotosViewModel : PhotosViewModel() {
    init {
        photosData.value = Photo.getList()
    }
}
