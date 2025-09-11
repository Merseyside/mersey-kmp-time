plugins {
    `kotlin-multiplatform-convention`
    with(catalogPlugins.plugins) {
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(moko.kswift)
    }
    `maven-publish-plugin`
}

kotlin {
    androidLibrary {
        namespace = "com.merseyside.merseyLib.time.coroutines"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.time)
            implementation(common.coroutines)
            implementation(common.mersey.kotlin.ext)
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