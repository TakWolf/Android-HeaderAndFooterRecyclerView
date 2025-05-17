plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.takwolf.android.hfrecyclerview.paging"
    compileSdk = 36

    defaultConfig {
        minSdk = 21

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
    compileOnly("androidx.lifecycle:lifecycle-common:2.9.0")
    compileOnly("androidx.recyclerview:recyclerview:1.4.0")
    compileOnly("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api(project(":hfrecyclerview"))
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.takwolf.android.hfrecyclerview"
            artifactId = "paging"
            version = "0.0.18"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
