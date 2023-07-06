package com.colorata.wallman.buildSrc

object Libraries {

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:7.4.0"
    }
    object AndroidX {
        const val core = "androidx.core:core-ktx:${LibraryVersions.AndroidX.core}"
        const val lifecycle =
            "androidx.lifecycle:lifecycle-runtime-ktx:${LibraryVersions.AndroidX.lifecycle}"
        const val lifecycleService = "androidx.lifecycle:lifecycle-service:${LibraryVersions.AndroidX.lifecycle}"
        const val lifecycleCompose = "androidx.lifecycle:lifecycle-runtime-compose:${LibraryVersions.AndroidX.lifecycle}"
        const val activity = "androidx.activity:activity-ktx:${LibraryVersions.AndroidX.activity}"
        const val dataStore = "androidx.datastore:datastore:${LibraryVersions.AndroidX.dataStore}"
        const val dataStoreCore = "androidx.datastore:datastore-core:${LibraryVersions.AndroidX.dataStore}"
        const val profileInstaller = "androidx.profileinstaller:profileinstaller:${LibraryVersions.AndroidX.profileInstaller}"
        const val startup = "androidx.startup:startup-runtime:${LibraryVersions.AndroidX.startup}"
        const val splashscreen = "androidx.core:core-splashscreen:${LibraryVersions.AndroidX.splashscreen}"
        const val work = "androidx.work:work-runtime-ktx:${LibraryVersions.AndroidX.work}"
    }

    object Test {
        const val junit = "junit:junit:${LibraryVersions.Test.junit}"
        const val androidXJunit = "androidx.test.ext:junit-ktx:${LibraryVersions.Test.androidXJunit}"
    }

    object Compose {
        const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:${LibraryVersions.Compose.base}"

        const val runtime = "androidx.compose.runtime:runtime:${LibraryVersions.Compose.base}"
        const val activity = "androidx.activity:activity-compose:${LibraryVersions.AndroidX.activity}"
        const val ui = "androidx.compose.ui:ui:${LibraryVersions.Compose.base}"
        const val uiUtil = "androidx.compose.ui:ui-util:${LibraryVersions.Compose.base}"
        const val uiTest = "androidx.compose.ui:ui-test-junit4-android:${LibraryVersions.Compose.base}"
        const val uiToolingManifest = "androidx.compose.ui:ui-test-manifest:${LibraryVersions.Compose.base}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${LibraryVersions.Compose.base}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${LibraryVersions.Compose.base}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${LibraryVersions.Compose.base}"
        const val material3 = "androidx.compose.material3:material3:${LibraryVersions.Compose.material3}"
        const val material3WindowSize = "androidx.compose.material3:material3-window-size-class:${LibraryVersions.Compose.material3}"

        const val materialMotionNavigation = "io.github.fornewid:material-motion-compose-navigation:${LibraryVersions.Compose.materialMotion}"
        const val materialMotionCore = "io.github.fornewid:material-motion-compose-core:${LibraryVersions.Compose.materialMotion}"

        const val coil = "io.coil-kt:coil-compose:${LibraryVersions.Compose.coil}"

        const val glance = "androidx.glance:glance:${LibraryVersions.Compose.glance}"
        const val glanceAppWidget = "androidx.glance:glance-appwidget:${LibraryVersions.Compose.glance}"
    }

    object Accompanist {
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${LibraryVersions.Accompanist.systemUiController}"
        const val flowLayout = "com.google.accompanist:accompanist-flowlayout:${LibraryVersions.Accompanist.flowLayout}"
    }

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${LibraryVersions.Kotlin.version}"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Kotlin.serialization}"
        const val collectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:${LibraryVersions.Kotlin.collectionsImmutable}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${LibraryVersions.Hilt.base}"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${LibraryVersions.Hilt.base}"
        const val compiler = "androidx.hilt:hilt-compiler:${LibraryVersions.Hilt.compiler}"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:${LibraryVersions.Hilt.navigationCompose}"
    }

    object Colorata {
        const val animateAsLifestyle = "com.gitlab.colorata:animateaslifestyle:${LibraryVersions.Colorata.animateAsLifestyle}"
    }

    object Koin {
        const val library = "io.insert-koin:koin-androidx-compose:${LibraryVersions.Koin.version}"
    }

    object Ktor {
        const val library = "io.ktor:ktor-client-android:${LibraryVersions.Ktor.version}"
    }
}