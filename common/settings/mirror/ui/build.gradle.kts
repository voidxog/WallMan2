plugins {
    id("configuration")
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.ui

        +projects.common.settings.mirror.api
    }
    namespace = "settings.mirror.ui"
}