plugins {
    `nexus-config`
}

allprojects {
    version = "1.1.6"
    group = "io.github.merseyside"
}

buildscript { // disable pod install tasks until find a solution
    repositories {
        gradlePluginPortal()
    }

    if (!isBuildIos()) {
        with(project.gradle.startParameter.excludedTaskNames) {
            add("podImport")
            add("podInstall")
            add("podGenIOS")
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
