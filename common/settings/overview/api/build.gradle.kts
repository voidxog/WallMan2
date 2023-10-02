plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.core.data
    }
    namespace = "settings.overview.api"
}