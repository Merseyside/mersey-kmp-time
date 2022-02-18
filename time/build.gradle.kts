plugins {
    id(Plugins.androidConvention)
    id(Plugins.kotlinMultiplatformConvention)
    id(Plugins.kotlinKapt)
    id(Plugins.mobileMultiplatform)
    id(Plugins.kotlinSerialization)
    id(Plugins.iosFramework)
    id(Plugins.swiftPackage) version "2.0.3"
    `maven-publish-config`
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
    multiplatformLibs.serialization
)

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    if (isLocalKotlinExtLibrary()) {
        commonMainImplementation(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainImplementation(common.merseyLib.kotlin.ext)
    }

    mppLibs.forEach { commonMainImplementation(it) }

    coreLibraryDesugaring(androidLibs.desugarJdk)
}
