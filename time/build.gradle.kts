plugins {
    `kotlin-multiplatform-convention`
    with(catalogPlugins.plugins) {
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(kotlin.serialization)
        plugin(moko.kswift)
    }
    `maven-publish-plugin`
}

kotlin {
    androidLibrary {
        namespace = "com.merseyside.merseyLib.time"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(multiplatformLibs.settings)
            implementation(common.mersey.kotlin.ext)
            implementation(common.serialization)
            api(multiplatformLibs.moko.kswift)
        }
    }
}

kotlinExtension {
    setCompilerArgs("-Xskip-prerelease-check")
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}