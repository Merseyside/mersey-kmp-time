plugins {
    `kotlin-multiplatform-convention`
    with(catalogPlugins.plugins) {
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(kotlin.serialization)
        id(cocoapods.id())
    }
    `maven-publish-plugin`
}

kotlin {
    androidLibrary {
        namespace = "com.merseyside.merseyLib.time"
    }

    val isMac = System.getProperty("os.name").startsWith("Mac OS")

    if (isMac) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()

        cocoapods {
            summary = "Time module"
            version = common.versions.mersey.time.get()
            homepage = "https://github.com/Merseyside/mersey-kmp-time"
            ios.deploymentTarget = iosLibs.versions.deploymentTarget.get()
            framework {
                baseName = "TimeCore"
                isStatic = false
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(multiplatformLibs.settings)
            implementation(common.mersey.kotlin.ext)
            implementation(common.serialization)
        }
    }
}

kotlinExtension {
    setCompilerArgs("-Xskip-prerelease-check")
}