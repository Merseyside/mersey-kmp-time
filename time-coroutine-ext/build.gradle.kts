@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(moko.multiplatform)
        plugin(moko.kswift)
    }
    `maven-publish-config`
}

android {
    namespace = "com.merseyside.merseyLib.time.coroutines"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
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
    commonMainImplementation(projects.time)
    commonMainImplementation(common.coroutines)

    if (isLocalKotlinExtLibrary()) {
        commonMainImplementation(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainImplementation(common.mersey.kotlin.ext)
    }

    commonMainApi(multiplatformLibs.moko.kswift)
}
