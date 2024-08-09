package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow

class MultiPhotoListViewModel : ViewModel() {
    val photos1 = MutableStateFlow(Photo.newList(20))
    val photos2 = MutableStateFlow(Photo.newList(20))
}
