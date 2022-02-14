package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ExtraLiveHolder
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ListLiveHolder

class MultiListViewModel : ViewModel() {
    val extraHolder1 = ExtraLiveHolder()
    val extraHolder2 = ExtraLiveHolder()
    val photosHolder1 = ListLiveHolder(Photo.getList())
    val photosHolder2 = ListLiveHolder(Photo.getList())
}
