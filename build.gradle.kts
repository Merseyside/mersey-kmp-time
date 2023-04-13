plugins {
    `nexus-config`
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

allprojects {
    plugins.withId("org.gradle.maven-publish") {
        version = common.versions.mersey.time.get()
        group = "io.github.merseyside"
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
