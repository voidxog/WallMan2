import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.colorata.wallman.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    implementation("com.android.tools.build:gradle:8.1.1")
}

gradlePlugin {
    plugins {
        register("configuration") {
            id = "configuration"
            implementationClass = "ConfigurationPlugin"
        }
    }
}