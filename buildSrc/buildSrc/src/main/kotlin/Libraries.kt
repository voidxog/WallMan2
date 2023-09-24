object Libraries {

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
        const val material3WindowSize =
            "androidx.compose.material3:material3-window-size-class:${LibraryVersions.Compose.material3}"

        const val navigation = "androidx.navigation:navigation-compose:${LibraryVersions.Compose.navigation}"

        const val glance = "androidx.glance:glance:${LibraryVersions.Compose.glance}"
        const val glanceAppWidget = "androidx.glance:glance-appwidget:${LibraryVersions.Compose.glance}"
    }

}

class Libs(val applier: (String) -> Unit) {

    val android = Android()

    inner class Android {
        fun gradlePlugin() = applier("com.android.tools.build:gradle:8.3.0-alpha03")
    }

    val androidX = AndroidX()

    inner class AndroidX {
        fun core() = applier("androidx.core:core-ktx:${LibraryVersions.AndroidX.core}")
        fun lifecycle() = applier("androidx.lifecycle:lifecycle-runtime-ktx:${LibraryVersions.AndroidX.lifecycle}")

        fun lifecycleService() = applier(
            "androidx.lifecycle:lifecycle-service:${LibraryVersions.AndroidX.lifecycle}"
        )

        fun lifecycleCompose() =
            applier("androidx.lifecycle:lifecycle-runtime-compose:${LibraryVersions.AndroidX.lifecycle}")

        fun lifecycleViewModel() =
            applier("androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersions.AndroidX.lifecycle}")

        fun activity() = applier(
            "androidx.activity:activity-ktx:${LibraryVersions.AndroidX.activity}"
        )

        fun dataStore() = applier(
            "androidx.datastore:datastore:${LibraryVersions.AndroidX.dataStore}"
        )

        fun dataStoreCore() = applier(
            "androidx.datastore:datastore-core:${LibraryVersions.AndroidX.dataStore}"
        )


        fun palette() = applier("androidx.palette:palette-ktx:1.0.0")
        fun profileInstaller() =
            applier("androidx.profileinstaller:profileinstaller:${LibraryVersions.AndroidX.profileInstaller}")

        fun startup() = applier(
            "androidx.startup:startup-runtime:${LibraryVersions.AndroidX.startup}"
        )

        fun splashscreen() = applier(
            "androidx.core:core-splashscreen:${LibraryVersions.AndroidX.splashscreen}"
        )

        fun work() = applier("androidx.work:work-runtime-ktx:${LibraryVersions.AndroidX.work}")
    }

    val test = Test()

    inner class Test {
        fun junit() = applier(
            "junit:junit:${LibraryVersions.Test.junit}"
        )

        fun androidXJunit() = applier("androidx.test.ext:junit-ktx:${LibraryVersions.Test.androidXJunit}")
    }

    val compose = Compose()

    inner class Compose {
        fun gradlePlugin() = applier(
            "org.jetbrains.compose:compose-gradle-plugin:${LibraryVersions.Compose.base}"
        )

        fun runtime() = applier(
            "androidx.compose.runtime:runtime:${LibraryVersions.Compose.base}"
        )

        fun activity() = applier(
            "androidx.activity:activity-compose:${LibraryVersions.AndroidX.activity}"
        )

        fun ui() = applier(
            "androidx.compose.ui:ui:${LibraryVersions.Compose.base}"
        )

        fun uiUtil() = applier(
            "androidx.compose.ui:ui-util:${LibraryVersions.Compose.base}"
        )

        fun uiTest() = applier(
            "androidx.compose.ui:ui-test-junit4-android:${LibraryVersions.Compose.base}"
        )

        fun uiToolingManifest() = applier(
            "androidx.compose.ui:ui-test-manifest:${LibraryVersions.Compose.base}"
        )

        fun uiTestManifest() = applier(
            "androidx.compose.ui:ui-test-manifest:${LibraryVersions.Compose.base}"
        )

        fun uiToolingPreview() = applier(
            "androidx.compose.ui:ui-tooling-preview:${LibraryVersions.Compose.base}"
        )

        fun uiTooling() = applier(
            "androidx.compose.ui:ui-tooling:${LibraryVersions.Compose.base}"
        )

        fun material3() = applier(
            "androidx.compose.material3:material3:${LibraryVersions.Compose.material3}"
        )

        fun material3WindowSize() =
            applier("androidx.compose.material3:material3-window-size-class:${LibraryVersions.Compose.material3}")

        fun materialColorUtilities() =
            applier("dev.sasikanth:material-color-utilities:1.0.0-alpha01")

        fun navigation() = applier(
            "androidx.navigation:navigation-compose:${LibraryVersions.Compose.navigation}"
        )

        fun glance() = applier(
            "androidx.glance:glance:${LibraryVersions.Compose.glance}"
        )

        fun glanceAppWidget() = applier("androidx.glance:glance-appwidget:${LibraryVersions.Compose.glance}")

        fun rebugger() = applier("com.github.theapache64:rebugger:1.0.0-alpha06")
    }

    val accompanist = Accompanist()

    inner class Accompanist {
        fun systemUiController() =
            applier("com.google.accompanist:accompanist-systemuicontroller:${LibraryVersions.Accompanist.systemUiController}")
    }

    val kotlin = Kotlin()

    inner class Kotlin {
        fun gradlePlugin() = applier(
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${LibraryVersions.Kotlin.version}"
        )

        fun serialization() =
            applier("org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Kotlin.serialization}")

        fun collectionsImmutable() =
            applier("org.jetbrains.kotlinx:kotlinx-collections-immutable:${LibraryVersions.Kotlin.collectionsImmutable}")

        fun coroutines() = applier("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    }

    val colorata = Colorata()

    inner class Colorata {
        fun animateAsLifestyle() =
            applier("com.gitlab.colorata:animateaslifestyle:${LibraryVersions.Colorata.animateAsLifestyle}")
    }

    val ktor = Ktor()

    inner class Ktor {
        fun library() = applier("io.ktor:ktor-client-android:${LibraryVersions.Ktor.version}")
    }
}