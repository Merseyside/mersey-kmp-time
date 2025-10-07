enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        google()

        gradlePluginPortal()
    }

    val catalogVersions = "1.8.5"
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

        val catalogPlugins by creating {
            from("$group:catalog-version-plugins:$catalogVersions")
        }

        val catalogGradle by creating {
            from("$group:catalog-version-gradle:$catalogVersions")
        }

        val iosLibs by creating {
            from("$group:catalog-version-ios:$catalogVersions")
        }
    }
}

include(":time-core", ":time-coroutine-ext")

val isCompositeBuild = gradle.parent != null
if (!isCompositeBuild) {
    include(":android-app")
}

rootProject.name = "kmm-time-library"