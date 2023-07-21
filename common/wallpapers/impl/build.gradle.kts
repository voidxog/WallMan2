plugins {
    multiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
        core.di()
        wallpapers.api()
    }
}

androidNamespace("wallpapers.impl")