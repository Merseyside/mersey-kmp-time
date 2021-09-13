import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(LibraryDeps.Plugins.androidLibrary)
    plugin(LibraryDeps.Plugins.kotlinMultiplatform)
    plugin(LibraryDeps.Plugins.kotlinKapt)
    plugin(LibraryDeps.Plugins.mobileMultiplatform)
    plugin(LibraryDeps.Plugins.kotlinSerialization)
    plugin(LibraryDeps.Plugins.mavenPublish)
    plugin(LibraryDeps.Plugins.iosFramework)
    plugin(LibraryDeps.Plugins.swiftPackage) version "2.0.3"
}

group = LibraryVersions.Application.groupId
version = LibraryVersions.Application.version

android {
    compileSdkVersion(LibraryVersions.Application.compileSdk)

    defaultConfig {
        minSdkVersion(LibraryVersions.Application.minSdk)
        targetSdkVersion(LibraryVersions.Application.targetSdk)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xinline-classes", "-Xopt-in=org.mylibrary.OptInAnnotation")
    }
}

kotlin {
    multiplatformSwiftPackage {
        packageName("Time")
        swiftToolsVersion("5.3")
        targetPlatforms {
            iOS { v("13") }
        }
        outputDirectory(File(rootDir, "/TimePackage"))
    }

    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
}

val mppLibs = listOf(
    LibraryDeps.Libs.MultiPlatform.kotlinStdLib,
    LibraryDeps.Libs.MultiPlatform.serializationJson,
)

dependencies {
    mppLibs.forEach { mppLibrary(it) }
}

framework {
    mppLibs.forEach { export(it) }
}
