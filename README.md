# Android - HeaderAndFooterRecyclerView

[![Android](https://img.shields.io/badge/android-23%2B-brightgreen)](https://developer.android.com)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.takwolf.android.hfrecyclerview/hfrecyclerview)](https://central.sonatype.com/artifact/io.github.takwolf.android.hfrecyclerview/hfrecyclerview)

一个让 `RecyclerView` 支持 `HeaderView` 和 `FooterView` 的解决方案。

特性如下：

- 不改变 `RecyclerView` 基本使用方式，对业务 `Adapter` 无侵入，也不需要额外的 `Adapter` 包装
- 无需处理 `HeaderView` 和 `FooterView` 的重用问题
- 支持 `LinearLayoutManager`、`GridLayoutManager` 和 `StaggeredGridLayoutManager` 的横向和纵向布局
- 支持多个 `HeaderView` 和 `FooterView`，并且可以动态添加和删除
- 支持动态切换 `Adapter` 和 `LayoutManager`
- 提供额外分页组件，方便快速实现「下拉刷新」和「加载更多」功能

## Snippets

```kotlin
implementation("androidx.core:core-ktx:1.17.0")
implementation("androidx.recyclerview:recyclerview:1.4.0")
implementation("io.github.takwolf.android.hfrecyclerview:hfrecyclerview:0.0.0")
    
// 可选，分页组件。
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
implementation("io.github.takwolf.android.hfrecyclerview:paging:0.0.0")
```

## Usage

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
