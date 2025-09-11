plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    androidLibrary {
        compileSdk = androidLibsBuild.version("compileSdk").toInt()
        minSdk = androidLibsBuild.version("compileMinSdk").toInt()
    }

    val isMac = System.getProperty("os.name").startsWith("Mac OS")

    if (isMac) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }
}
