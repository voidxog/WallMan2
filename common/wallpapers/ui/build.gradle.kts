plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        wallpapers.api()
        core.data()
        core.ui()
        categories.api()
    }
}

androidNamespace("wallpapers.ui")