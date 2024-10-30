dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()

        google()
        gradlePluginPortal()
    }

    val catalogVersions = "1.8.3"
    val group = "io.github.merseyside"
    versionCatalogs {

        val catalogGradle by creating {
            from("$group:catalog-version-gradle:$catalogVersions")
        }
    }
}