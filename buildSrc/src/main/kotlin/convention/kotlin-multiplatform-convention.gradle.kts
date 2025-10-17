plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("io.github.merseyside.kotlin-extension")
}

kotlin {
    androidLibrary {
        compileSdk = androidLibsBuild.version("compileSdk").toInt()
        minSdk = androidLibsBuild.version("compileMinSdk").toInt()
    }

    ifBuildIos {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }
}
