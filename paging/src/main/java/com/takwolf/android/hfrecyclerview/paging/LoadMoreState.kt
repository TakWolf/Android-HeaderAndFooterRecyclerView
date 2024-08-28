package com.takwolf.android.hfrecyclerview.paging

enum class LoadMoreState(val canLoadMore: Boolean) {
    DISABLED(false),
    IDLE(true),
    LOADING(false),
    FINISHED(false),
    FAILED(true),
}
