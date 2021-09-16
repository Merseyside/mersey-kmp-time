enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    val catalogVersions = "1.0.4"
    val group = "io.github.merseyside"
    versionCatalogs {
        create("multiplatformLibs") {
            from("$group:catalog-version-multiplatform:$catalogVersions")
        }

        create("common") {
            from("$group:catalog-version-common:$catalogVersions")
        }

        create("androidLibs") {
            from("$group:catalog-version-android:$catalogVersions")
        }
    }
}

include(
    ":time",
    ":android-app",
)

rootProject.name = "kmm-time-library"