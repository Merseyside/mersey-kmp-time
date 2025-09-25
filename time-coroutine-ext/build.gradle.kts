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
            implementation(projects.timeCore)
            implementation(common.coroutines)
            implementation(common.mersey.kotlin.ext)
        }
    }
}

kotlinExtension {
    setCompilerArgs("-Xskip-prerelease-check")
}