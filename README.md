# Android - HeaderAndFooterRecyclerView

[![Android](https://img.shields.io/badge/android-23%2B-brightgreen)](https://developer.android.com)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.takwolf.android.hfrecyclerview/hfrecyclerview)](https://central.sonatype.com/artifact/io.github.takwolf.android.hfrecyclerview/hfrecyclerview)

一个让 `RecyclerView` 支持 `HeaderView` 和 `FooterView` 的解决方案。

特性如下：

- 不改变 `RecyclerView` 的基本使用方式，对业务 `Adapter` 无侵入，也不需要额外的 `Adapter` 包装
- 支持多个 `HeaderView` 和 `FooterView`，并且可以动态添加删除，无需手动处理重用问题
- 支持 `LinearLayoutManager`、`GridLayoutManager` 和 `StaggeredGridLayoutManager` 布局管理器
- 提供额外分页组件，方便快速实现「下拉刷新」和「加载更多」功能

## Snippets

```kotlin
implementation("androidx.recyclerview:recyclerview:1.4.0")
implementation("io.github.takwolf.android.hfrecyclerview:hfrecyclerview:0.0.1")
    
// 可选，分页组件。
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
implementation("io.github.takwolf.android.hfrecyclerview:paging:0.0.1")
```

## Usage

### 基本用法

使用 `HeaderAndFooterRecyclerView` 替换默认的 `RecyclerView`：

```xml
<com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

添加 `HeaderView` 和 `FooterView` 的方式如下：

```kotlin
val recyclerView = binding.recyclerView
recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.adapter = adapter
        
val headerView = layoutInflater.inflate(R.layout.header, recyclerView.headerViewContainer, false)
recyclerView.addHeaderView(headerView)
        
val footerView = layoutInflater.inflate(R.layout.footer, recyclerView.footerViewContainer, false)
recyclerView.addFooterView(footerView)
```

### 加载更多

首先创建布局 `footer_load_more.xml`：

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
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

然后实现 `AppLoadMoreFooter`：

```kotlin
class AppLoadMoreFooter private constructor(
    private val binding: FooterLoadMoreBinding,
) : LoadMoreFooter(binding.root) {
    companion object {
        fun create(recyclerView: HeaderAndFooterRecyclerView): AppLoadMoreFooter {
            val binding = FooterLoadMoreBinding.inflate(LayoutInflater.from(recyclerView.context), recyclerView.footerViewContainer, false)
            return AppLoadMoreFooter(binding)
        }
    }

    init {
        // 手动点击也会触发「加载更多」
        binding.tvText.setOnClickListener {
            checkDoLoadMore()
        }

        // 设置滑动到倒数第 5 个位置，就会触发「加载更多」
        preloadOffset = 5
    }

    override fun onUpdateViews() {
        when (state) {
            // 禁用
            LoadMoreState.DISABLED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            // 空闲，可触发加载更多数据
            LoadMoreState.IDLE -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = true
            }
            // 加载中
            LoadMoreState.LOADING -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.tvText.visibility = View.INVISIBLE
                binding.tvText.text = null
                binding.tvText.isClickable = false
            }
            // 列表加载完成，没有更多数据
            LoadMoreState.FINISHED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.load_more_finished)
                binding.tvText.isClickable = false
            }
            // 加载失败，可触发重试
            LoadMoreState.FAILED -> {
                binding.loadingBar.visibility = View.INVISIBLE
                binding.tvText.visibility = View.VISIBLE
                binding.tvText.setText(R.string.load_more_failed)
                binding.tvText.isClickable = true
            }
        }
    }
}
```

最后，将 `AppLoadMoreFooter` 挂载到列表中，并监听加载更多事件：

```kotlin
val loadMoreFooter = AppLoadMoreFooter.create(recyclerView).apply {
    addToRecyclerView(recyclerView)
}
loadMoreFooter.onLoadMoreListener = LoadMoreFooter.OnLoadMoreListener {
    // 处理「加载更多」事件
}
```

在合适的时机，通过修改 `loadMoreFooter.state` 来改变视图状态。

### 分页组件

以下演示一个完整的「下拉刷新」和「加载更多」功能。

「下拉刷新」以 `SwipeRefreshLayout` 为例，你可以更换成任意你喜欢的其它实现。

页面如下布局：

```xml
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    android:id="@+id/refresh_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```

`AppLoadMoreFooter` 于上一节相同。

根据 Jetpack 架构，创建对应的 MVVM 结构。其中 ViewModel 可能是这样的：

```kotlin
class TopicPagingViewModel() : ViewModel() {
    // 列表数据源，用于更新 `Adapter`
    val topics = MutableStateFlow(emptyList<Topic>())

    // 错误消息事件，用于显示 `Toast`
    val errorEvent = MutableLiveData<Event<String>>()

    private var page = 0

    // 分页数据源模版
    // 需要实现 `doRefresh()` 和 `doLoadMore()`
    // `dataVersion` 用于标记请求的数据版本，如果于当前数据版本不一致，应该忽略
    val pagingSource = object : PagingSource() {
        override fun doRefresh(dataVersion: Int) {
            viewModelScope.launch {
                try {
                    val result = CNodeClient.api.getTopics(limit = 20)
                    if (onRefreshSuccess(dataVersion, result.data.isEmpty())) {
                        topics.value = result.data
                        page = 1
                    }
                } catch (e: Exception) {
                    if (onRefreshFailure(dataVersion)) {
                        errorEvent.value = Event(e.message ?: "refresh error")
                    }
                }
            }
        }

        override fun doLoadMore(dataVersion: Int) {
            viewModelScope.launch {
                try {
                    val result = CNodeClient.api.getTopics(page = page + 1, limit = 20)
                    if (onLoadMoreSuccess(dataVersion, result.data.isEmpty())) {
                        topics.value += result.data
                        page += 1
                    }
                } catch (e: Exception) {
                    // 防止失败过快而形成 DDOS
                    delay(1000)

                    if (onLoadMoreFailure(dataVersion)) {
                        errorEvent.value = Event(e.message ?: "load more error")
                    }
                }
            }
        }
    }

    init {
        // 初始化时自动触发「下拉刷新」
        pagingSource.refresh()
    }
}
```

最后，监听数据变化：

```kotlin
// 设置「下拉刷新」和「加载更多」事件监听
refreshLayout.setOnRefreshListener {
    viewModel.pagingSource.refresh()
}
loadMoreFooter.onLoadMoreListener = LoadMoreFooter.OnLoadMoreListener {
    viewModel.pagingSource.loadMore()
}

// 监听「下拉刷新」和「加载更多」视图状态
viewModel.pagingSource.refreshState.observe(owner, refreshLayout)
viewModel.pagingSource.loadMoreState.observe(owner, loadMoreFooter)

// 监听列表变化
viewModel.topics.observe(owner) { topics ->
    adapter.submitList(topics)
}

// 监听错误消息
viewModel.errorEvent.observe(owner) { event ->
    event.handleValue()?.let { message ->
        showToast(message)
    }
}
```

「下拉刷新」和「加载更多」的完整示例，也可以查看该项目：[Android-RefreshAndLoadMore-Demo](https://github.com/TakWolf/Android-RefreshAndLoadMore-Demo)

## License

[Apache License 2.0](LICENSE)
