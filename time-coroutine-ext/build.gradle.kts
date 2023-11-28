@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(moko.kswift)
    }
    `maven-publish-plugin`
}

android {
    namespace = "com.merseyside.merseyLib.time.coroutines"
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
    iosSimulatorArm64()
    iosX64()

    applyDefaultHierarchyTemplate()
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
        commonMainImplementation(project(":kotlin-ext"))
    } else {
        commonMainImplementation(common.mersey.kotlin.ext)
    }

    commonMainApi(multiplatformLibs.moko.kswift)
}
