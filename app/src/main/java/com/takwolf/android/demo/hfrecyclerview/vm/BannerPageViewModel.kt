package com.takwolf.android.demo.hfrecyclerview.vm

import com.takwolf.android.demo.hfrecyclerview.model.Banner

class BannerPageViewModel : ListViewModel<Banner>() {
    init {
        entitiesData.value = Banner.getList()
    }
}
