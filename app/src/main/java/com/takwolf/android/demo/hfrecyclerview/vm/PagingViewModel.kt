package com.takwolf.android.demo.hfrecyclerview.vm

import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.hfrecyclerview.vm.holder.PhotoPagingLiveHolder
import com.takwolf.android.demo.hfrecyclerview.vm.holder.ToastLiveHolder

class PagingViewModel : ViewModel() {
    val toastHolder = ToastLiveHolder()
    val photosHolder = PhotoPagingLiveHolder(this, toastHolder)
}
