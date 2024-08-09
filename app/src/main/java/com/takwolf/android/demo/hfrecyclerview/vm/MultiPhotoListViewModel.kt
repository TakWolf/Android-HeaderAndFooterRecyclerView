package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.entity.Photo

class MultiPhotoListViewModel : ViewModel() {
    val photos1 = MutableLiveData(List(20) { Photo.new() })
    val photos2 = MutableLiveData(List(20) { Photo.new() })
}
