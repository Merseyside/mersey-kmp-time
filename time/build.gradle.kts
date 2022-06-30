@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.convention.id())
        id(mersey.kotlin.convention.id())
        plugin(kotlin.kapt)
        id(cocoapods.id())
        plugin(moko.multiplatform)
        plugin(kotlin.serialization)
        plugin(swiftPackage)
        plugin(moko.kswift)
        `maven-publish-config`
    }
}

android {
    namespace = "com.merseyside.merseyLib.time"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    ios()
    iosSimulatorArm64()

    sourceSets {
        val iosMain by getting
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)
    }

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
            version = common.versions.time.get()

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

androidConvention {
    sourceSets {
        setSourceSets = false
    }

}

kotlinConvention {
    setCompilerArgs("-Xskip-prerelease-check")
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

    commonMainApi(multiplatformLibs.moko.kswift)
    mppLibs.forEach { commonMainImplementation(it) }

    coreLibraryDesugaring(androidLibs.desugarJdk)
}
