package com.takwolf.android.demo.hfrecyclerview.vm

import com.takwolf.android.demo.hfrecyclerview.data.Photo

class PhotoListViewModel : ListViewModel<Photo>() {
    init {
        entitiesData.value = Photo.getList()
    }
}
