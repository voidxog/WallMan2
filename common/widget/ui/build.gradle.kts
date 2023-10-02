plugins {
    id("configuration")
}

configuration {
    modules {
        +projects.common.core.data
        +projects.common.core.ui

        +projects.common.widget.api
    }
    namespace = "widget.ui"
}