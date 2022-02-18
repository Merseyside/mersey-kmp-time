plugins {
    id(Plugins.androidConvention)
    id(Plugins.kotlinMultiplatformConvention)
    id(Plugins.kotlinKapt)
    id(Plugins.mobileMultiplatform)
    id(Plugins.kotlinSerialization)
    id(Plugins.iosFramework)
    id(Plugins.swiftPackage) version "2.0.3"
    id(Plugins.kSwift)
    `maven-publish-config`
}

kotlin {

    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    ios()
    // Add the ARM64 simulator target
//    iosSimulatorArm64()
//
//    val iosMain by sourceSets.getting
//    val iosSimulatorArm64Main by sourceSets.getting
//
//    // Set up dependencies between the source sets
//    iosSimulatorArm64Main.dependsOn(iosMain)


    multiplatformSwiftPackage {
        packageName("Time")
        swiftToolsVersion("5.3")
        targetPlatforms {
            iOS { v("13") }
        }
        outputDirectory(File(rootDir, "/TimePackage"))
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

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}

dependencies {
    if (isLocalKotlinExtLibrary()) {
        commonMainImplementation(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainImplementation(common.merseyLib.kotlin.ext)
    }

    commonMainApi(multiplatformLibs.kswift)
    mppLibs.forEach { commonMainImplementation(it) }

    coreLibraryDesugaring(androidLibs.desugarJdk)
}
