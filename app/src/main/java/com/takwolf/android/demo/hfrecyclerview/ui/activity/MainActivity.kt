package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.takwolf.android.demo.hfrecyclerview.R
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityMainBinding
import com.takwolf.android.demo.hfrecyclerview.model.DemoConfigs
import com.takwolf.android.demo.hfrecyclerview.vm.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rgLayoutManagerType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_linear_layout -> {
                    viewModel.configs.value = viewModel.configs.value.copy(
                        layoutManagerType = DemoConfigs.LayoutManagerType.LINEAR,
                    )
                }
                R.id.rb_grid_layout -> {
                    viewModel.configs.value = viewModel.configs.value.copy(
                        layoutManagerType = DemoConfigs.LayoutManagerType.GRID,
                    )
                }
                R.id.rb_staggered_grid_layout -> {
                    viewModel.configs.value = viewModel.configs.value.copy(
                        layoutManagerType = DemoConfigs.LayoutManagerType.STAGGERED_GRID,
                    )
                }
            }
        }

        binding.rgOrientation.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_vertical -> {
                    viewModel.configs.value = viewModel.configs.value.copy(
                        orientation = RecyclerView.VERTICAL,
                    )
                }
                R.id.rb_horizontal -> {
                    viewModel.configs.value = viewModel.configs.value.copy(
                        orientation = RecyclerView.HORIZONTAL,
                    )
                }
            }
        }

        binding.swEnableRefresh.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                enableRefresh = isChecked,
            )
        }

        binding.swEnableLoadMore.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                enableLoadMore = isChecked,
            )
        }

        binding.swAddBannerHeader.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                addBannerHeader = isChecked,
            )
        }

        binding.swAddStaticHeader.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                addStaticHeader = isChecked,
            )
        }

        binding.swAddStaticFooter.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                addStaticFooter = isChecked,
            )
        }

        binding.swReverseLayout.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                reverseLayout = isChecked,
            )
        }

        binding.swStackFromEnd.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                stackFromEnd = isChecked,
            )
        }

        binding.swLayoutDirectionRtl.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                layoutDirectionRtl = isChecked,
            )
        }

        binding.swNotFullPage.setOnCheckedChangeListener { _, isChecked ->
            viewModel.configs.value = viewModel.configs.value.copy(
                notFullPage = isChecked,
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.configs.collect { configs ->
                    when (configs.layoutManagerType) {
                        DemoConfigs.LayoutManagerType.LINEAR -> {
                            binding.rbLinearLayout.isChecked = true
                        }
                        DemoConfigs.LayoutManagerType.GRID -> {
                            binding.rbGridLayout.isChecked = true
                        }
                        DemoConfigs.LayoutManagerType.STAGGERED_GRID -> {
                            binding.rbStaggeredGridLayout.isChecked = true
                        }
                    }

                    when (configs.orientation) {
                        RecyclerView.VERTICAL -> {
                            binding.rbVertical.isChecked = true
                        }
                        RecyclerView.HORIZONTAL -> {
                            binding.rbHorizontal.isChecked = true
                        }
                    }

                    binding.swEnableRefresh.isEnabled = configs.orientation == RecyclerView.VERTICAL && !configs.reverseLayout
                    binding.swStackFromEnd.isEnabled = configs.layoutManagerType == DemoConfigs.LayoutManagerType.LINEAR

                    binding.swEnableRefresh.isChecked = configs.enableRefresh
                    binding.swEnableLoadMore.isChecked = configs.enableLoadMore
                    binding.swAddBannerHeader.isChecked = configs.addBannerHeader
                    binding.swAddStaticHeader.isChecked = configs.addStaticHeader
                    binding.swAddStaticFooter.isChecked = configs.addStaticFooter
                    binding.swReverseLayout.isChecked = configs.reverseLayout
                    binding.swStackFromEnd.isChecked = configs.stackFromEnd
                    binding.swLayoutDirectionRtl.isChecked = configs.layoutDirectionRtl
                    binding.swNotFullPage.isChecked = configs.notFullPage
                }
            }
        }

        binding.btnDemo.setOnClickListener {
            DemoActivity.open(this, viewModel.configs.value)
        }
    }
}
