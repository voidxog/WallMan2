plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
        core.di()
        core.ui()

        widget.api()
    }
}