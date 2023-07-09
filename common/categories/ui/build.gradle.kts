import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies

plugins {
    composeMultiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
        core.di()
        core.ui()

        wallpapers.api()
        wallpapers.ui()

        categories.api()
    }
}