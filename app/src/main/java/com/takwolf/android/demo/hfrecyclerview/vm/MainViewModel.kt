package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.model.DemoConfigs
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
    val configs = MutableStateFlow(DemoConfigs())
}
