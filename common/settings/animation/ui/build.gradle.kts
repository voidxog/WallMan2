plugins {
    id("configuration")
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.ui

        +projects.common.settings.animation.api
    }
    namespace = "settings.animation.ui"
}