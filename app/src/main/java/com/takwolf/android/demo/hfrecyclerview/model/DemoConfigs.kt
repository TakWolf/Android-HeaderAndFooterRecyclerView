package com.takwolf.android.demo.hfrecyclerview.model

import android.content.Intent
import androidx.recyclerview.widget.HFRVHack.RecyclerView

data class DemoConfigs(
    var layoutManagerType: LayoutManagerType = LayoutManagerType.LINEAR,
    var orientation: Int = RecyclerView.VERTICAL,
    var enableRefresh: Boolean = false,
    var enableLoadMore: Boolean = false,
    var addBannerHeader: Boolean = false,
    var addStaticHeader: Boolean = false,
    var addStaticFooter: Boolean = false,
    var reverseLayout: Boolean = false,
    var isRTL: Boolean = false,
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
        private const val EXTRA_IS_RTL = "isRTL"

        fun getFromIntentExtra(intent: Intent): DemoConfigs {
            return DemoConfigs(
                layoutManagerType = intent.getStringExtra(EXTRA_LAYOUT_MANAGER_TYPE)?.let { LayoutManagerType.valueOf(it) } ?: LayoutManagerType.LINEAR,
                orientation = intent.getIntExtra(EXTRA_ORIENTATION, RecyclerView.VERTICAL),
                enableRefresh = intent.getBooleanExtra(EXTRA_ENABLE_REFRESH, false),
                enableLoadMore = intent.getBooleanExtra(EXTRA_ENABLE_LOAD_MORE, false),
                addBannerHeader = intent.getBooleanExtra(EXTRA_ADD_BANNER_HEADER, false),
                addStaticHeader = intent.getBooleanExtra(EXTRA_ADD_STATIC_HEADER, false),
                addStaticFooter = intent.getBooleanExtra(EXTRA_ADD_STATIC_FOOTER, false),
                reverseLayout = intent.getBooleanExtra(EXTRA_REVERSE_LAYOUT, false),
                isRTL = intent.getBooleanExtra(EXTRA_IS_RTL, false),
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
        intent.putExtra(EXTRA_IS_RTL, isRTL)
    }
}
