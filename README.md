# Android - HeaderAndFooterRecyclerView

[![Platform](https://img.shields.io/badge/platform-Android-brightgreen)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/github/license/TakWolf/Android-HeaderAndFooterRecyclerView)](LICENSE)
[![JitPack](https://jitpack.io/v/TakWolf/Android-HeaderAndFooterRecyclerView.svg)](https://jitpack.io/#TakWolf/Android-HeaderAndFooterRecyclerView)

`RecyclerView` 添加 `HeaderView` 和 `FooterView` 的解决方案。

拥有如下特性：

- 对业务 `Adapter` 无侵入，也不需要额外的包装 `Adapter`。
- `HeaderView` 和 `FooterView` 直接挂载在 `RecyclerView` 上，无需手动处理重用问题。
- 支持 `LinearLayoutManager`、`GridLayoutManager` 和 `StaggeredGridLayoutManager` 三种布局管理器的横向和纵向布局。
- 支持多个 `HeaderView` 和 `FooterView`，并且可以动态添加和删除。
- 支持动态切换 `Adapter` 和 `LayoutManager`。
- 额外提供分页组件，方便快速实现「下拉刷新」和「加载更多」功能。

## Usage

```kotlin
repositories { 
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:hfrecyclerview:0.0.18")
    // 可选。分页组件。
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
    implementation("androidx.lifecycle:lifecycle-common:2.8.7")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:paging:0.0.18")
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

## License

[Apache License 2.0](LICENSE)
