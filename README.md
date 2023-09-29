# Android - HeaderAndFooterRecyclerView

[![Platform](https://img.shields.io/badge/platform-Android-brightgreen)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/github/license/TakWolf/Android-HeaderAndFooterRecyclerView)](https://www.apache.org/licenses/LICENSE-2.0)
[![JitPack](https://jitpack.io/v/TakWolf/Android-HeaderAndFooterRecyclerView.svg)](https://jitpack.io/#TakWolf/Android-HeaderAndFooterRecyclerView)

一个为 `RecyclerView` 添加 `HeaderView` 和 `FooterView` 的方案。特征如下：

- 对业务 `Adapter` 无侵入，也不需要额外的 `WrapperAdapter`。
- `HeaderView` 和 `FooterView` 直接挂载在 `RecyclerView` 上，无需手动处理重用问题。（特别适合将 `ViewPager` 用作 `HeaderView` 的场景）
- 支持 `LinearLayoutManager`、`GridLayoutManager` 和 `StaggeredGridLayoutManager` 三种布局管理器的横向和纵向布局。
- 支持多个 `HeaderView` 和 `FooterView`，并且可以动态添加和删除。
- 支持动态切换 `Adapter` 和 `LayoutManager`。

## Usage

```kotlin
repositories { 
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:hfrecyclerview:0.0.3")
}
```

```xml
<com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

```kotlin
val recyclerView = findViewById<HeaderAndFooterRecyclerView>(R.id.recycler_view)
recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.adapter = adapter
        
val headerView = layoutInflater.inflate(R.layout.header, recyclerView.headerViewContainer, false)
recyclerView.addHeaderView(headerView)
        
val footerView = layoutInflater.inflate(R.layout.footer, recyclerView.footerViewContainer, false)
recyclerView.addFooterView(footerView)
```

## LoadMoreFooter

该项目也提供了一个 `LoadMoreFooter` 用于实现加载更多的功能，通常配合下拉刷新组件一起使用。

```kotlin
dependencies {
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:loadmorefooter:0.0.3")
}
```

你需要为 `LoadMoreFooter` 提供一个布局，示例如下：

[footer_load_more.xml](app/src/main/res/layout/footer_load_more.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ProgressBar
        android:id="@+id/loading_bar"
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:layout_gravity="center"
        android:layout_margin="16dp"
        android:indeterminateTint="@color/app_primary"
        android:visibility="invisible"
        tools:visibility="visible" />

    <TextView
        android:id="@+id/tv_text"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:textColor="?android:textColorSecondary"
        android:textSize="14sp"
        android:gravity="center"
        android:background="?selectableItemBackground"
        android:visibility="invisible"
        tools:text="@string/load_more_finished"
        tools:visibility="visible" />

</FrameLayout>
```

然后创建一个 `LoadMoreFooter` 的实现，示例如下（示例使用了 `ViewBinding`）：

[LoadMoreFooter.kt](app/src/main/java/com/takwolf/android/demo/hfrecyclerview/ui/widget/LoadMoreFooter.kt)

```kotlin
class LoadMoreFooter(private val binding: FooterLoadMoreBinding) : com.takwolf.android.hfrecyclerview.loadmorefooter.LoadMoreFooter(binding.root) {
    companion object {
        fun create(layoutInflater: LayoutInflater, recyclerView: HeaderAndFooterRecyclerView): LoadMoreFooter {
            val binding = FooterLoadMoreBinding.inflate(layoutInflater, recyclerView.footerViewContainer, false)
            return LoadMoreFooter(binding)
        }
    }

    init {
        binding.tvText.setOnClickListener {
            checkDoLoadMore()
        }
        preloadOffset = 1
    }

    override fun onUpdateViews(footerView: View, @State state: Int) {
        when (state) {
            STATE_DISABLED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            STATE_LOADING -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            STATE_FINISHED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.load_more_finished)
                binding.tvText.isClickable = false
            }
            STATE_ENDLESS -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = true
            }
            STATE_FAILED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.load_more_failed)
                binding.tvText.isClickable = true
            }
        }
    }
}
```

最后挂载到 `RecyclerView` 上：

```kotlin
val loadMoreFooter = LoadMoreFooter.create(layoutInflater, binding.recyclerView)
loadMoreFooter.setOnLoadMoreListener { 
    // do load more here
}
loadMoreFooter.addToRecyclerView(binding.recyclerView)
```

通过 `loadMoreFooter.state` 来改变组件状态。

在 [Android-RefreshAndLoadMore-Demo](https://github.com/TakWolf/Android-RefreshAndLoadMore-Demo) 提供了一个更详细的例子用于演示下拉刷新和加载更多。

## License

```
Copyright 2022 TakWolf

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
