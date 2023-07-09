import com.colorata.wallman.buildSrc.modules
import com.colorata.wallman.buildSrc.projectDependencies

plugins {
    multiplatformSetup()
}

projectDependencies {
    modules {
        core.data()
    }
}