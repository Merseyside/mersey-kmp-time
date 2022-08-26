plugins {
    `nexus-config`
}

allprojects {
    version = "1.1.6"
    group = "io.github.merseyside"
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
