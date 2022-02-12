package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.Banner

class ExtraInfoListViewModel : ViewModel() {
    val headerInfos: MutableList<Banner> = Banner.getList(2)
    val footerInfos: MutableList<Banner> = Banner.getList(2)
}
