plugins {
    configuration()
}

configuration {
    modules {
        +projects.common.core.data
    }
    namespace = "settings.animation.api"
}