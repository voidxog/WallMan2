plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
        core.ui()

        settings.animation.api()
    }
}

androidNamespace("settings.animation.ui")