plugins {
    alias(libs.plugins.configuration)
}

configuration {
    androidMain {
        internal {
            +libs.androidx.work
            +libs.compose.glance
            +libs.compose.glance.appwidget
        }
    }
    modules {
        +projects.common.core.data
        +projects.common.core.di

        +projects.common.widget.api

        +projects.common.wallpapers.api
    }
    namespace = "widget.ui_widget"
}