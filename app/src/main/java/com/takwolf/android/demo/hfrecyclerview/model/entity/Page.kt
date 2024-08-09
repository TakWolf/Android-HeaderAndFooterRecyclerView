package com.takwolf.android.demo.hfrecyclerview.model.entity;

data class Page<Data>(
    val list: List<Data>,
    val hasMore: Boolean,
)
