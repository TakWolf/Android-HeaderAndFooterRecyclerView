package com.takwolf.android.hfrecyclerview.paging

enum class RefreshState(val canRefresh: Boolean) {
    DISABLED(false),
    IDLE(true),
    LOADING(false),
    FAILED(true),
}
