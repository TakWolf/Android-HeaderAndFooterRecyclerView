package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ExtraLiveHolder
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ListLiveHolder

class SingleListViewModel : ViewModel() {
    val extraHolder = ExtraLiveHolder()
    val photosHolder = ListLiveHolder(Photo.getList())
}
