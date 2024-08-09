package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow

class PhotoListViewModel : ViewModel() {
    val photos = MutableStateFlow(Photo.newList(20))
}
