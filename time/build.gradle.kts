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

dependencies {
    mppLibs.forEach { commonMainImplementation(it) }
}

framework {
    mppLibs.forEach { export(it) }
}
