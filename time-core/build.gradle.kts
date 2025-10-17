plugins {
    `kotlin-multiplatform-convention`
    with(catalogPlugins.plugins) {
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(kotlin.serialization)
    }
    `maven-publish-plugin`
}

val isBuildIos = isBuildIos()
if (isBuildIos) {
    pluginManager.apply(catalogPlugins.plugins.cocoapods.id())
}

kotlin {
    androidLibrary {
        namespace = "com.merseyside.merseyLib.time"
    }

    if (isBuildIos) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()

        extensions.configure<org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension> {
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
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

kotlinExtension {
    setCompilerArgs("-Xskip-prerelease-check")
}