plugins {
    `nexus-config`
}

allprojects {
    version = "1.1.5"
    group = "io.github.merseyside"
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
