plugins {
    alias(libs.plugins.configuration)
}

configuration {
    modules {
        +projects.common.core.data
    }
    namespace = "settings.mirror.api"
}