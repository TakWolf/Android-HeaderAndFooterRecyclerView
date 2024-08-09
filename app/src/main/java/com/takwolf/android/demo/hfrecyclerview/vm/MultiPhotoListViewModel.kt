package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Photo

class MultiPhotoListViewModel : ViewModel() {
    val photos1 = MutableLiveData(Photo.newList(20))
    val photos2 = MutableLiveData(Photo.newList(20))
}
