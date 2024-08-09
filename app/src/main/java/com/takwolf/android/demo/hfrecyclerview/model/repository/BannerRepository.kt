package com.takwolf.android.demo.hfrecyclerview.model.repository;

import com.takwolf.android.demo.hfrecyclerview.model.entity.Banner
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class BannerRepository {
    suspend fun getList(size: Int = 5): List<Banner> = coroutineScope {
        delay(1000)
        List(size) { Banner.new() }
    }
}
