package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.data.ColorInfo

class ExtraInfoListViewModel : ViewModel() {
    val headerInfos: MutableList<ColorInfo> = ColorInfo.getList(2)
    val footerInfos: MutableList<ColorInfo> = ColorInfo.getList(2)
}
