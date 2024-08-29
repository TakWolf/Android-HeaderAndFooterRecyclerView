package com.takwolf.android.demo.hfrecyclerview.model

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.RecyclerView

data class DemoConfigs(
    var layoutManagerType: LayoutManagerType = LayoutManagerType.LINEAR,
    var orientation: Int = RecyclerView.VERTICAL,
    var enableRefresh: Boolean = false,
    var enableLoadMore: Boolean = false,
    var addBannerHeader: Boolean = false,
    var addStaticHeader: Boolean = false,
    var addStaticFooter: Boolean = false,
    var reverseLayout: Boolean = false,
    var stackFromEnd: Boolean = false,
    var layoutDirectionRtl: Boolean = false,
    var notFullPage: Boolean = false,
) {
    enum class LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID,
    }

    companion object {
        private const val EXTRA_LAYOUT_MANAGER_TYPE = "layoutManagerType"
        private const val EXTRA_ORIENTATION = "orientation"
        private const val EXTRA_ENABLE_REFRESH = "enableRefresh"
        private const val EXTRA_ENABLE_LOAD_MORE = "enableLoadMore"
        private const val EXTRA_ADD_BANNER_HEADER = "addBannerHeader"
        private const val EXTRA_ADD_STATIC_HEADER = "addStaticHeader"
        private const val EXTRA_ADD_STATIC_FOOTER = "addStaticFooter"
        private const val EXTRA_REVERSE_LAYOUT = "reverseLayout"
        private const val EXTRA_STACK_FROM_END = "stackFromEnd"
        private const val EXTRA_LAYOUT_DIRECTION_RTL = "layoutDirectionRtl"
        private const val EXTRA_NOT_FULL_PAGE = "notFullPage"

        fun getFromSavedStateHandle(savedStateHandle: SavedStateHandle): DemoConfigs {
            return DemoConfigs(
                layoutManagerType = savedStateHandle.get<String>(EXTRA_LAYOUT_MANAGER_TYPE)?.let { LayoutManagerType.valueOf(it) } ?: LayoutManagerType.LINEAR,
                orientation = savedStateHandle.get<Int>(EXTRA_ORIENTATION) ?: RecyclerView.VERTICAL,
                enableRefresh = savedStateHandle.get<Boolean>(EXTRA_ENABLE_REFRESH) ?: false,
                enableLoadMore = savedStateHandle.get<Boolean>(EXTRA_ENABLE_LOAD_MORE) ?: false,
                addBannerHeader = savedStateHandle.get<Boolean>(EXTRA_ADD_BANNER_HEADER) ?: false,
                addStaticHeader = savedStateHandle.get<Boolean>(EXTRA_ADD_STATIC_HEADER) ?: false,
                addStaticFooter = savedStateHandle.get<Boolean>(EXTRA_ADD_STATIC_FOOTER) ?: false,
                reverseLayout = savedStateHandle.get<Boolean>(EXTRA_REVERSE_LAYOUT) ?: false,
                stackFromEnd = savedStateHandle.get<Boolean>(EXTRA_STACK_FROM_END) ?: false,
                layoutDirectionRtl = savedStateHandle.get<Boolean>(EXTRA_LAYOUT_DIRECTION_RTL) ?: false,
                notFullPage = savedStateHandle.get<Boolean>(EXTRA_NOT_FULL_PAGE) ?: false,
            )
        }
    }

    fun putToIntentExtra(intent: Intent) {
        intent.putExtra(EXTRA_LAYOUT_MANAGER_TYPE, layoutManagerType.name)
        intent.putExtra(EXTRA_ORIENTATION, orientation)
        intent.putExtra(EXTRA_ENABLE_REFRESH, enableRefresh)
        intent.putExtra(EXTRA_ENABLE_LOAD_MORE, enableLoadMore)
        intent.putExtra(EXTRA_ADD_BANNER_HEADER, addBannerHeader)
        intent.putExtra(EXTRA_ADD_STATIC_HEADER, addStaticHeader)
        intent.putExtra(EXTRA_ADD_STATIC_FOOTER, addStaticFooter)
        intent.putExtra(EXTRA_REVERSE_LAYOUT, reverseLayout)
        intent.putExtra(EXTRA_STACK_FROM_END, stackFromEnd)
        intent.putExtra(EXTRA_LAYOUT_DIRECTION_RTL, layoutDirectionRtl)
        intent.putExtra(EXTRA_NOT_FULL_PAGE, notFullPage)
    }
}
