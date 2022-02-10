enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

private val isLocalKotlinExtLibrary = false

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    val catalogVersions = "1.2.8"
    val group = "io.github.merseyside"
    versionCatalogs {
        val multiplatformLibs by creating {
            from("$group:catalog-version-multiplatform:$catalogVersions")
        }

        val common by creating {
            from("$group:catalog-version-common:$catalogVersions")
        }

        val androidLibs by creating {
            from("$group:catalog-version-android:$catalogVersions")
        }
    }
}

include(
    ":time",
    ":android-app",
)

if (isLocalKotlinExtLibrary) {
    include(":kotlin-ext")
    project(":kotlin-ext").projectDir =
        File(rootDir.parent, "mersey-kotlin-ext/kotlin-ext")
}

rootProject.name = "kmm-time-library"