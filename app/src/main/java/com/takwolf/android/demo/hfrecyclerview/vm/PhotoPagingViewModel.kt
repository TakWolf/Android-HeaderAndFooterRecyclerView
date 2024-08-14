package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takwolf.android.demo.hfrecyclerview.vm.source.PhotoPagingSource

class PhotoPagingViewModel : ViewModel() {
    val pagingSource = PhotoPagingSource(viewModelScope)

    init {
        pagingSource.refresh()
    }
}
