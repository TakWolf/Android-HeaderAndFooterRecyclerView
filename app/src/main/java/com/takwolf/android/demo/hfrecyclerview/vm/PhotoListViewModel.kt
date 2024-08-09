package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.entity.Photo

class PhotoListViewModel : ViewModel() {
    val photos = MutableLiveData(List(20) { Photo.new() })
}
