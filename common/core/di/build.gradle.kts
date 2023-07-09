import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies

plugins {
    multiplatformSetup()
    serialization()
}

projectDependencies {
    modules {
        wallpapers.api()

        widget.api()
        core.data()
    }
}