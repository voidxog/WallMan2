import com.colorata.wallman.buildSrc.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    //kotlin("kapt")
    id("app.cash.molecule")
}

apply<AppDefaultPlugin>()

android {

    kotlinOptions.apply {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.8.21"
        )
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}"
        )
    }
}

dependencies {
    implementation(Libraries.AndroidX.core)
    implementation(Libraries.AndroidX.lifecycle)
    implementation(Libraries.AndroidX.lifecycleCompose)
    implementation(Libraries.AndroidX.lifecycleService)
    implementation(Libraries.AndroidX.activity)

    testImplementation(Libraries.Test.junit)
    androidTestImplementation(Libraries.Test.androidXJunit)

    debugImplementation(Libraries.Compose.uiToolingManifest)
    debugImplementation(Libraries.Compose.uiTooling)
    implementation(Libraries.Compose.uiToolingPreview)
    debugImplementation(Libraries.Compose.uiTestManifest)

    implementation(Libraries.Compose.activity)
    implementation(Libraries.Compose.ui)
    implementation(Libraries.Compose.uiUtil)
    implementation(Libraries.Compose.material3)
    implementation(Libraries.Compose.material3WindowSize)

    implementation(Libraries.Accompanist.systemUiController)
    implementation(Libraries.Accompanist.flowLayout)

    implementation(Libraries.Compose.materialMotionCore)
    implementation(Libraries.Compose.materialMotionNavigation)

    implementation(Libraries.AndroidX.dataStore)
    implementation(Libraries.AndroidX.dataStoreCore)
    implementation(Libraries.Kotlin.collectionsImmutable)
    implementation(Libraries.Kotlin.serialization)

    implementation(Libraries.Compose.coil)

    implementation(Libraries.Compose.glance)
    implementation(Libraries.Compose.glanceAppWidget)

    implementation(Libraries.Colorata.animateAsLifestyle)

    implementation(Libraries.AndroidX.profileInstaller)
    implementation(Libraries.AndroidX.startup)
    implementation(Libraries.AndroidX.splashscreen)
    implementation(Libraries.AndroidX.work)
    implementation(Libraries.Ktor.library)
}

gradle.taskGraph.whenReady {
    val file = file(rootDir.absolutePath + "/app/build/outputs/apk/release/app-release.apk")
    copy {
        from(file.absolutePath)
        into(rootDir.absolutePath)
    }
    println(file.absolutePath)
}