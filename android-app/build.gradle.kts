import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(Plugins.androidApplication)
    plugin(Plugins.kotlinAndroid)
    plugin(Plugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        applicationId = AndroidConfig.APPLICATION_ID

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        vectorDrawables.useSupportLibrary = AndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures.dataBinding = true

    dexOptions {
        javaMaxHeapSize = "2g"
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets.getByName("main") {
        res.srcDir("src/main/res/")
        res.srcDir("src/main/res/layouts/fragments")
        res.srcDir("src/main/res/layouts/activity")
        res.srcDir("src/main/res/layouts/dialog")
        res.srcDir("src/main/res/layouts/views")
        res.srcDir("src/main/res/value/values-light")
        res.srcDir("src/main/res/value/values-night")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val androidLibs = listOf(
    LibraryDeps.Libs.appCompat,
    LibraryDeps.Libs.material
)

val merseyLibs = listOf(
    LibraryDeps.Libs.MerseyLibs.archy,
    LibraryDeps.Libs.MerseyLibs.utils
)

val merseyModules = listOf(
    LibraryModules.archy,
    LibraryModules.utils
)

dependencies {
    androidLibs.forEach { lib -> implementation(lib) }
    merseyLibs.forEach { lib -> implementation(lib) }

    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
}
