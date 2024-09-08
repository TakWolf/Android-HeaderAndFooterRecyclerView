package com.takwolf.android.demo.hfrecyclerview.model

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoConfigs(
    val layoutManagerType: LayoutManagerType = LayoutManagerType.LINEAR,
    val orientation: Int = RecyclerView.VERTICAL,
    val enableRefresh: Boolean = false,
    val enableLoadMore: Boolean = false,
    val addBannerHeader: Boolean = false,
    val addStaticHeader: Boolean = false,
    val addStaticFooter: Boolean = false,
    val reverseLayout: Boolean = false,
    val stackFromEnd: Boolean = false,
    val layoutDirectionRtl: Boolean = false,
    val notFullPage: Boolean = false,
) : Parcelable {
    enum class LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID,
    }
}
