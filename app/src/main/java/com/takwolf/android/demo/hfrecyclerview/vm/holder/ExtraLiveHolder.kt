package com.takwolf.android.demo.hfrecyclerview.vm.holder

import android.view.LayoutInflater
import com.takwolf.android.demo.hfrecyclerview.databinding.*
import com.takwolf.android.demo.hfrecyclerview.model.Banner
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class ExtraLiveHolder() {
    private val headers = Banner.getList(2)
    private val footers = Banner.getList(2)

    private fun addVerticalHeader(
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val headerBinding = HeaderVerticalBinding.inflate(layoutInflater, recyclerView.headerViewContainer, false)
        headerBinding.tvName.setBackgroundColor(banner.color)
        headerBinding.btnItem.setOnLongClickListener {
            headers.remove(banner)
            recyclerView.removeHeaderView(headerBinding.root)
            true
        }
        recyclerView.addHeaderView(headerBinding.root)
    }

    private fun addVerticalFooter(
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val footerBinding = FooterVerticalBinding.inflate(layoutInflater, recyclerView.footerViewContainer, false)
        footerBinding.tvName.setBackgroundColor(banner.color)
        footerBinding.btnItem.setOnLongClickListener {
            footers.remove(banner)
            recyclerView.removeFooterView(footerBinding.root)
            true
        }
        recyclerView.addFooterView(footerBinding.root)
    }

    private fun addHorizontalHeader(
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val headerBinding = HeaderHorizontalBinding.inflate(layoutInflater, recyclerView.headerViewContainer, false)
        headerBinding.tvName.setBackgroundColor(banner.color)
        headerBinding.btnItem.setOnLongClickListener {
            headers.remove(banner)
            recyclerView.removeHeaderView(headerBinding.root)
            true
        }
        recyclerView.addHeaderView(headerBinding.root)
    }

    private fun addHorizontalFooter(
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val footerBinding = FooterHorizontalBinding.inflate(layoutInflater, recyclerView.footerViewContainer, false)
        footerBinding.tvName.setBackgroundColor(banner.color)
        footerBinding.btnItem.setOnLongClickListener {
            footers.remove(banner)
            recyclerView.removeFooterView(footerBinding.root)
            true
        }
        recyclerView.addFooterView(footerBinding.root)
    }

    fun setupVertical(
        recyclerView: HeaderAndFooterRecyclerView,
        binding: WidgetHfDashboardBinding? = null,
    ) {
        for (banner in headers) {
            addVerticalHeader(recyclerView, banner)
        }
        for (banner in footers) {
            addVerticalFooter(recyclerView, banner)
        }
        binding?.let {
            it.btnAddHeader.setOnClickListener {
                val banner = Banner()
                headers.add(banner)
                addVerticalHeader(recyclerView, banner)
            }
            it.btnRemoveHeader.setOnClickListener {
                if (headers.isNotEmpty()) {
                    headers.removeLast()
                    recyclerView.removeHeaderView(recyclerView.headerViewsCount - 1)
                }
            }
            it.btnAddFooter.setOnClickListener {
                val banner = Banner()
                footers.add(banner)
                addVerticalFooter(recyclerView, banner)
            }
            it.btnRemoveFooter.setOnClickListener {
                if (footers.isNotEmpty()) {
                    footers.removeLast()
                    recyclerView.removeFooterView(recyclerView.footerViewsCount - 1)
                }
            }
        }
    }

    fun setupHorizontal(
        recyclerView: HeaderAndFooterRecyclerView,
        binding: WidgetHfDashboardBinding? = null,
    ) {
        for (banner in headers) {
            addHorizontalHeader(recyclerView, banner)
        }
        for (banner in footers) {
            addHorizontalFooter(recyclerView, banner)
        }
        binding?.let {
            it.btnAddHeader.setOnClickListener {
                val banner = Banner()
                headers.add(banner)
                addHorizontalHeader(recyclerView, banner)
            }
            it.btnRemoveHeader.setOnClickListener {
                if (headers.isNotEmpty()) {
                    headers.removeLast()
                    recyclerView.removeHeaderView(recyclerView.headerViewsCount - 1)
                }
            }
            it.btnAddFooter.setOnClickListener {
                val banner = Banner()
                footers.add(banner)
                addHorizontalFooter(recyclerView, banner)
            }
            it.btnRemoveFooter.setOnClickListener {
                if (footers.isNotEmpty()) {
                    footers.removeLast()
                    recyclerView.removeFooterView(recyclerView.footerViewsCount - 1)
                }
            }
        }
    }
}
