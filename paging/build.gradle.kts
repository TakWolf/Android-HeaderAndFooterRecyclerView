plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "com.takwolf.android.hfrecyclerview.paging"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 23

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    compileOnly("androidx.lifecycle:lifecycle-common:2.10.0")
    compileOnly("androidx.recyclerview:recyclerview:1.4.0")
    compileOnly("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api(project(":hfrecyclerview"))
}

mavenPublishing {
    coordinates("io.github.takwolf.android.hfrecyclerview", "paging", "0.0.2")

    pom {
        name.set("Android-HeaderAndFooterRecyclerView")
        description.set("Let RecyclerView support header and footer views.")
        url.set("https://github.com/TakWolf/Android-HeaderAndFooterRecyclerView")
        inceptionYear.set("2025")
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("TakWolf")
                name.set("TakWolf")
                url.set("https://github.com/TakWolf")
            }
        }
        scm {
            url.set("https://github.com/TakWolf/Android-HeaderAndFooterRecyclerView")
            connection.set("scm:git:git://github.com/TakWolf/Android-HeaderAndFooterRecyclerView.git")
            developerConnection.set("scm:git:ssh://git@github.com/TakWolf/Android-HeaderAndFooterRecyclerView.git")
        }
    }

    publishToMavenCentral()
    signAllPublications()
}
