# Android - HeaderAndFooterRecyclerView

[![Platform](https://img.shields.io/badge/platform-Android-brightgreen)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/github/license/TakWolf/Android-HeaderAndFooterRecyclerView)](https://www.apache.org/licenses/LICENSE-2.0)
[![JitPack](https://jitpack.io/v/TakWolf/Android-HeaderAndFooterRecyclerView.svg)](https://jitpack.io/#TakWolf/Android-HeaderAndFooterRecyclerView)

一个为 `RecyclerView` 添加 `HeaderView` 和 `FooterView` 的方案。特征如下：

- 对业务 `Adapter` 无侵入，也不需要额外的包装 `Adapter`。
- `HeaderView` 和 `FooterView` 直接挂载在 `RecyclerView` 上，无需手动处理重用问题。
- 支持 `LinearLayoutManager`、`GridLayoutManager` 和 `StaggeredGridLayoutManager` 三种布局管理器的横向和纵向布局。
- 支持多个 `HeaderView` 和 `FooterView`，并且可以动态添加和删除。
- 支持动态切换 `Adapter` 和 `LayoutManager`。

## Usage

```kotlin
repositories { 
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:hfrecyclerview:0.0.3")
    // 可选。分页组件。
    implementation("com.github.TakWolf.Android-HeaderAndFooterRecyclerView:paging:0.0.3")
}
```

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
