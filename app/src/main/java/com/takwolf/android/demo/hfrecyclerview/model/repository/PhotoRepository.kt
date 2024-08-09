package com.takwolf.android.demo.hfrecyclerview.model.repository;

import com.takwolf.android.demo.hfrecyclerview.model.entity.Page
import com.takwolf.android.demo.hfrecyclerview.model.entity.Photo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class PhotoRepository {
    suspend fun getPage(pageNum: Int = 0, pageSize: Int = 20): Page<Photo> = coroutineScope {
        delay(1000)
        if (pageNum <= 4) {
            Page(List(pageSize) { Photo.new() }, pageNum < 4)
        } else {
            Page(emptyList(), false)
        }
    }
}
