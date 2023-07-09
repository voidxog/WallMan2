plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
        core.di()
        core.ui()

        wallpapers.api()

        settings.overview.api()
        settings.memory.api()
        settings.mirror.api()
        settings.about.api()
    }
}