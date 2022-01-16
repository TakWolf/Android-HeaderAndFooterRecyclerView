package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ListViewModel<Entity> : ViewModel() {
    val entitiesData: MutableLiveData<MutableList<Entity>> = MutableLiveData()
}
