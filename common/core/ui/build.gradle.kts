plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
    }
    external {
        colorata.animateAsLifestyle()

        androidX.lifecycleViewModel()
        androidX.lifecycleCompose()

        compose.navigation()
        compose.material3WindowSize()
        compose.uiUtil()
        compose.uiToolingPreview()
    }

    internal {
        kotlin.collectionsImmutable()
        accompanist.systemUiController()
    }
}