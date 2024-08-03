package com.takwolf.android.demo.hfrecyclerview.vm.holder

import android.view.LayoutInflater
import com.takwolf.android.demo.hfrecyclerview.databinding.FooterHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.FooterVerticalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderHorizontalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.HeaderVerticalBinding
import com.takwolf.android.demo.hfrecyclerview.databinding.WidgetHfDashboardBinding
import com.takwolf.android.demo.hfrecyclerview.model.Banner
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

class ExtraLiveHolder {
    private val headers = Banner.getList(2).toMutableList()
    private val footers = Banner.getList(2).toMutableList()

    private fun addVerticalHeader(
        layoutInflater: LayoutInflater,
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
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
        layoutInflater: LayoutInflater,
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
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
        layoutInflater: LayoutInflater,
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
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
        layoutInflater: LayoutInflater,
        recyclerView: HeaderAndFooterRecyclerView,
        banner: Banner,
    ) {
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
        layoutInflater: LayoutInflater,
        recyclerView: HeaderAndFooterRecyclerView,
        binding: WidgetHfDashboardBinding? = null,
    ) {
        for (banner in headers) {
            addVerticalHeader(layoutInflater, recyclerView, banner)
        }
        for (banner in footers) {
            addVerticalFooter(layoutInflater, recyclerView, banner)
        }
        binding?.let {
            it.btnAddHeader.setOnClickListener {
                val banner = Banner()
                headers.add(banner)
                addVerticalHeader(layoutInflater, recyclerView, banner)
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
                addVerticalFooter(layoutInflater, recyclerView, banner)
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
        layoutInflater: LayoutInflater,
        recyclerView: HeaderAndFooterRecyclerView,
        binding: WidgetHfDashboardBinding? = null,
    ) {
        for (banner in headers) {
            addHorizontalHeader(layoutInflater, recyclerView, banner)
        }
        for (banner in footers) {
            addHorizontalFooter(layoutInflater, recyclerView, banner)
        }
        binding?.let {
            it.btnAddHeader.setOnClickListener {
                val banner = Banner()
                headers.add(banner)
                addHorizontalHeader(layoutInflater, recyclerView, banner)
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
                addHorizontalFooter(layoutInflater, recyclerView, banner)
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
