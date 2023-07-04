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
