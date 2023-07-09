plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        wallpapers.api()
        core.data()
        core.di()
        core.ui()
    }
}