package com.takwolf.android.demo.hfrecyclerview.vm

import com.takwolf.android.demo.hfrecyclerview.data.ColorInfo

class BannerPageViewModel : ListViewModel<ColorInfo>() {
    init {
        entitiesData.value = ColorInfo.getList()
    }
}
