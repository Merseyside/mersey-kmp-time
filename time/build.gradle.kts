@Suppress("DSL_SCOPE_VIOLATION")
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
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
    }
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()
//    sourceSets {
//        val iosMain by getting
//        val iosSimulatorArm64Main by getting
//        iosSimulatorArm64Main.dependsOn(iosMain)
//    }

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
            embedBitcode(org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.BITCODE)
            podfile = project.file("../ios-app-swiftui/Podfile")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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

val commonLibs = listOf(
    common.serialization
)

dependencies {
    commonMainImplementation(multiplatformLibs.settings)

    if (isLocalKotlinExtLibrary()) {
        commonMainImplementation(project(":kotlin-ext"))
    } else {
        commonMainImplementation(common.mersey.kotlin.ext)
    }

    commonMainApi(multiplatformLibs.moko.kswift)
    commonLibs.forEach { commonMainImplementation(it) }

    coreLibraryDesugaring(androidLibs.desugarJdk)
}