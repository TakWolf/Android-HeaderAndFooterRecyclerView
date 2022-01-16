package com.takwolf.android.demo.hfrecyclerview.helper

import android.view.LayoutInflater
import com.takwolf.android.demo.hfrecyclerview.data.ColorInfo
import com.takwolf.android.demo.hfrecyclerview.databinding.*
import com.takwolf.android.demo.hfrecyclerview.vm.ExtraInfoListViewModel
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView

object ExtraInfoViewHelper {
    fun setupVertical(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
    ) {
        for (colorInfo in viewModel.headerInfos) {
            addVerticalHeader(viewModel, recyclerView, colorInfo)
        }
        for (colorInfo in viewModel.footerInfos) {
            addVerticalFooter(viewModel, recyclerView, colorInfo)
        }
    }

    fun setupHorizontal(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
    ) {
        for (colorInfo in viewModel.headerInfos) {
            addHorizontalHeader(viewModel, recyclerView, colorInfo)
        }
        for (colorInfo in viewModel.footerInfos) {
            addHorizontalFooter(viewModel, recyclerView, colorInfo)
        }
    }

    fun listenVertical(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
        binding: WidgetHfDashboardBinding,
    ) {
        binding.btnAddHeader.setOnClickListener {
            val colorInfo = ColorInfo()
            viewModel.headerInfos.add(colorInfo)
            addVerticalHeader(viewModel, recyclerView, colorInfo)
        }
        binding.btnRemoveHeader.setOnClickListener {
            if (viewModel.headerInfos.isNotEmpty()) {
                viewModel.headerInfos.removeLast()
                recyclerView.removeHeaderView(recyclerView.headerViewsCount - 1)
            }
        }
        binding.btnAddFooter.setOnClickListener {
            val colorInfo = ColorInfo()
            viewModel.footerInfos.add(colorInfo)
            addVerticalFooter(viewModel, recyclerView, colorInfo)
        }
        binding.btnRemoveFooter.setOnClickListener {
            if (viewModel.footerInfos.isNotEmpty()) {
                viewModel.footerInfos.removeLast()
                recyclerView.removeFooterView(recyclerView.footerViewsCount - 1)
            }
        }
    }

    fun listenHorizontal(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
        binding: WidgetHfDashboardBinding,
    ) {
        binding.btnAddHeader.setOnClickListener {
            val colorInfo = ColorInfo()
            viewModel.headerInfos.add(colorInfo)
            addHorizontalHeader(viewModel, recyclerView, colorInfo)
        }
        binding.btnRemoveHeader.setOnClickListener {
            if (viewModel.headerInfos.isNotEmpty()) {
                viewModel.headerInfos.removeLast()
                recyclerView.removeHeaderView(recyclerView.headerViewsCount - 1)
            }
        }
        binding.btnAddFooter.setOnClickListener {
            val colorInfo = ColorInfo()
            viewModel.footerInfos.add(colorInfo)
            addHorizontalFooter(viewModel, recyclerView, colorInfo)
        }
        binding.btnRemoveFooter.setOnClickListener {
            if (viewModel.footerInfos.isNotEmpty()) {
                viewModel.footerInfos.removeLast()
                recyclerView.removeFooterView(recyclerView.footerViewsCount - 1)
            }
        }
    }

    private fun addVerticalHeader(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
        colorInfo: ColorInfo,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val headerBinding = HeaderVerticalBinding.inflate(layoutInflater, recyclerView.headerViewContainer, false)
        headerBinding.tvName.setBackgroundColor(colorInfo.color)
        headerBinding.btnItem.setOnLongClickListener {
            viewModel.headerInfos.remove(colorInfo)
            recyclerView.removeHeaderView(headerBinding.root)
            true
        }
        recyclerView.addHeaderView(headerBinding.root)
    }

    private fun addVerticalFooter(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
        colorInfo: ColorInfo,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val footerBinding = FooterVerticalBinding.inflate(layoutInflater, recyclerView.footerViewContainer, false)
        footerBinding.tvName.setBackgroundColor(colorInfo.color)
        footerBinding.btnItem.setOnLongClickListener {
            viewModel.footerInfos.remove(colorInfo)
            recyclerView.removeFooterView(footerBinding.root)
            true
        }
        recyclerView.addFooterView(footerBinding.root)
    }

    private fun addHorizontalHeader(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
        colorInfo: ColorInfo,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val headerBinding = HeaderHorizontalBinding.inflate(layoutInflater, recyclerView.headerViewContainer, false)
        headerBinding.tvName.setBackgroundColor(colorInfo.color)
        headerBinding.btnItem.setOnLongClickListener {
            viewModel.headerInfos.remove(colorInfo)
            recyclerView.removeHeaderView(headerBinding.root)
            true
        }
        recyclerView.addHeaderView(headerBinding.root)
    }

    private fun addHorizontalFooter(
        viewModel: ExtraInfoListViewModel,
        recyclerView: HeaderAndFooterRecyclerView,
        colorInfo: ColorInfo,
    ) {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val footerBinding = FooterHorizontalBinding.inflate(layoutInflater, recyclerView.footerViewContainer, false)
        footerBinding.tvName.setBackgroundColor(colorInfo.color)
        footerBinding.btnItem.setOnLongClickListener {
            viewModel.footerInfos.remove(colorInfo)
            recyclerView.removeFooterView(footerBinding.root)
            true
        }
        recyclerView.addFooterView(footerBinding.root)
    }
}
