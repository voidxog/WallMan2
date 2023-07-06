package com.colorata.wallman.buildSrc

object Modules {
    val shared = ":shared"
    object Core {
        private const val prefix = ":common:core"
        const val di = "$prefix:di"
        const val data = "$prefix:data"
        const val ui = "$prefix:ui"
        const val impl = "$prefix:impl"
    }

    object Wallpapers {
        private const val prefix = ":common:wallpapers"
        const val api = "$prefix:api"
        const val impl = "$prefix:impl"
        const val ui = "$prefix:ui"
    }

    object Categories {
        private const val prefix = ":common:categories"
        const val api = "$prefix:api"
        const val ui = "$prefix:ui"
    }

    object Settings {
        object Overview {
            private const val prefix = ":common:settings:overview"
            const val api = "$prefix:api"
            const val ui = "$prefix:ui"
        }

        object Memory {
            private const val prefix = ":common:settings:memory"
            const val api = "$prefix:api"
            const val ui = "$prefix:ui"
        }

        object Mirror {
            private const val prefix = ":common:settings:mirror"
            const val api = "$prefix:api"
            const val ui = "$prefix:ui"
        }

        object About {
            private const val prefix = ":common:settings:about"
            const val api = "$prefix:api"
            const val ui = "$prefix:ui"
        }
    }
}