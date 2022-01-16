package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.data.ColorInfo

class BannerViewModel : ViewModel() {
    val infosData = MutableLiveData(ColorInfo.getList())
}
