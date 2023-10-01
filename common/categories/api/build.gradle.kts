plugins {
    id("configuration")
}

configuration {
    modules {
        +projects.common.core.data
    }
    namespace = "categories.api"
}