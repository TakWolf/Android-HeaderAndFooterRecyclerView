plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.takwolf.android.hfrecyclerview"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    compileOnly("androidx.core:core-ktx:1.15.0")
    compileOnly("androidx.recyclerview:recyclerview:1.4.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.takwolf.android.hfrecyclerview"
            artifactId = "hfrecyclerview"
            version = "0.0.17"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
