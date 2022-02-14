package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Banner
import com.takwolf.android.demo.hfrecyclerview.model.Photo
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ListLiveHolder

class ExtraListViewModel : ViewModel() {
    val bannersHolder = ListLiveHolder(Banner.getList())
    val photosHolder = ListLiveHolder(Photo.getList())

    private val photosMap = HashMap<Int, List<Photo>>()

    fun loadListAt(position: Int) {
        val photos = photosMap.getOrPut(position) {
            Photo.getList()
        }
        photosHolder.setList(photos)
    }
}
