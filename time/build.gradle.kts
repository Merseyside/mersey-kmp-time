@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(Plugins.androidConvention)
    id(Plugins.kotlinMultiplatformConvention)
    id(Plugins.kotlinKapt)
    id(Plugins.mobileMultiplatform)
    id(Plugins.kotlinSerialization)
    id(Plugins.cocoaPods)
    id(Plugins.swiftPackage) version "2.0.3"
//    id(Plugins.kSwift)
    id(Plugins.mavenPublishConfig)

//    with(catalogPlugins.plugins) {
//        id(kotlinKapt.id())
//        id(nativeCocoaPods.id())
//        id(mobileMultiplatform.id())
//        id(kotlinSerialization.id())
//        alias(swiftPackage)
//    }
}

kotlin {

    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    ios()
    // Add the ARM64 simulator target
    iosSimulatorArm64()

    val iosMain by sourceSets.getting
    val iosSimulatorArm64Main by sourceSets.getting

    // Set up dependencies between the source sets
    iosSimulatorArm64Main.dependsOn(iosMain)


    multiplatformSwiftPackage {
        packageName("Time")
        swiftToolsVersion("5.3")
        targetPlatforms {
            iOS { v("13") }
        }
        outputDirectory(File(rootDir, "/TimePackage"))
    }

    cocoapods {

        framework {
            // Mandatory properties
            // Configure fields required by CocoaPods.
            summary = "KMM Time library"
            homepage = "https://github.com/Merseyside/mersey-kmp-time"
            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
            baseName = "KotlinTime"
            version = Metadata.version

            // Optional properties
            // (Optional) Dynamic framework support
            isStatic = false
            // (Optional) Dependency export
            transitiveExport = true
            // (Optional) Bitcode embedding
            embedBitcode(org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.BITCODE)
            podfile = project.file("../ios-app-swiftui/Podfile")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
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

//kswift {
//    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
//    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
//}

dependencies {
    if (isLocalKotlinExtLibrary()) {
        commonMainImplementation(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainImplementation(common.merseyLib.kotlin.ext)
    }

    //commonMainApi("dev.icerock.moko:kswift-runtime:0.3.0")
    mppLibs.forEach { commonMainImplementation(it) }

    coreLibraryDesugaring(androidLibs.desugarJdk)
}
