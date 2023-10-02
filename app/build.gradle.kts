plugins {
    id("serialization")
    id("app")
}

setupKotlin()

dependencies {
    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.test.juint)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.compose.ui.test)
    "baselineProfile"(projects.app.baselineprofile)

    debugImplementation(libs.compose.ui.tooling.manifest)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.test)

    moduleDependencies {
        +projects.shared
        +projects.common.core.data
        +projects.common.core.impl
        +projects.common.core.di
        +projects.common.settings.about.ui
        +projects.common.wallpapers.api
        +projects.common.widget.impl
        +projects.common.widget.uiWidget
    }
}

gradle.taskGraph.whenReady {
    val file = file(rootDir.absolutePath + "/app/build/outputs/apk/release/app-release.apk")
    copy {
        from(file.absolutePath)
        into(rootDir.absolutePath)
    }
    println(file.absolutePath)
}