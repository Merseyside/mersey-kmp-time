plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        id(cocoapods.id())
        plugin(kotlin.serialization)
        plugin(swiftPackage)
        plugin(moko.kswift)
    }
    `maven-publish-plugin`
}

android {
    namespace = "com.merseyside.merseyLib.time"
    compileSdk = androidLibs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = androidLibs.versions.compileMinSdk.get().toInt()
    }
}

kotlin {
    androidTarget()

    iosArm64()
    iosX64()
    iosSimulatorArm64()

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
            version = common.versions.mersey.time.get()

            // Optional properties
            // (Optional) Dynamic framework support
            isStatic = false
            // (Optional) Dependency export
            transitiveExport = true
            // (Optional) Bitcode embedding
            podfile = project.file("../ios-app-swiftui/Podfile")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }
}

androidExtension {
    sourceSets {
        setSourceSets = false
    }
}

kotlinExtension {
    setCompilerArgs("-Xskip-prerelease-check")
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}

dependencies {
    commonMainImplementation(multiplatformLibs.settings)
    commonMainImplementation(common.mersey.kotlin.ext)
    commonMainImplementation(common.serialization)

    commonMainApi(multiplatformLibs.moko.kswift)
}