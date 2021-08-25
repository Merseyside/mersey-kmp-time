object LibraryDeps {
    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")
        val mavenPublish = GradlePlugin(id = "maven-publish")
        val swiftPackage = GradlePlugin(id = "com.chromaticnoise.multiplatform-swiftpackage")

        val androidLibrary = GradlePlugin(
            id = "com.android.library",
            module = "com.android.tools.build:gradle:${LibraryVersions.Plugins.gradle}"
        )

        val kotlinMultiplatform = GradlePlugin(
            id = "org.jetbrains.kotlin.multiplatform",
            module = "org.jetbrains.kotlin:kotlin-gradle-plugin:${LibraryVersions.Plugins.kotlin}"
        )

        val kotlinSerialization = GradlePlugin(
            id = "kotlinx-serialization",
            module = "org.jetbrains.kotlin:kotlin-serialization:${LibraryVersions.Plugins.serialization}"
        )

        val kotlinParcelize = GradlePlugin(
            id = "kotlin-parcelize"
        )
    }

    object Libs {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersions.Common.coroutines}"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}"
        const val appCompat = "androidx.appcompat:appcompat:${LibraryVersions.Libs.appCompat}"
        const val material = "com.google.android.material:material:${LibraryVersions.Libs.material}"

        object MerseyLibs {
            private const val base = "com.github.Merseyside.mersey-android-library"
            const val archy = "$base:archy:${LibraryVersions.Common.merseyLibs}"
            const val utils = "$base:utils:${LibraryVersions.Common.merseyLibs}"
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = "org.jetbrains.kotlin:kotlin-stdlib:${LibraryVersions.Common.kotlinStdLib}",
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${LibraryVersions.Common.kotlinStdLib}"
            )
            val coroutines = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersions.Common.coroutines}",
                common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibraryVersions.Common.coroutines}",
                iosX64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:${LibraryVersions.Common.coroutines}",
                iosArm64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm64:${LibraryVersions.Common.coroutines}"
            )
            val serialization = MultiPlatformLibrary(
                common = "org.jetbrains.kotlinx:kotlinx-serialization-core:${LibraryVersions.Common.serialization}"
            )
            val serializationJson = MultiPlatformLibrary(
                common = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}"
            )
        }
    }
}