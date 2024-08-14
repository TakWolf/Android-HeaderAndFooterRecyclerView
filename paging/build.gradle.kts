plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.takwolf.android.hfrecyclerview.loadmorefooter"
    compileSdk = 35

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
    compileOnly("androidx.lifecycle:lifecycle-common:2.8.4")
    compileOnly("androidx.recyclerview:recyclerview:1.3.2")
    compileOnly("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    api(project(":hfrecyclerview"))
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.takwolf.android.hfrecyclerview"
            artifactId = "loadmorefooter"
            version = "0.0.4"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
