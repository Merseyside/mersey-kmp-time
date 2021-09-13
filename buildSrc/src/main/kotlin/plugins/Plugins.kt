/**
 * Configuration of all gradle build plugins
 */
object Plugins {
    val androidApplication = GradlePlugin(id = "com.android.application")
    val androidLibrary = GradlePlugin(id = "com.android.library")
    val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
    val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
    val kotlinAndroid = GradlePlugin(id = "kotlin-android")
    val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
    val updateDependencies = GradlePlugin(id = "plugins.update-dependencies")
    val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")

    val sqlDelight = GradlePlugin(
        id = "com.squareup.sqldelight",
        module = "com.squareup.sqldelight:gradle-core.plugins.plugin:${LibraryVersions.Plugins.sqlDelight}"
    )

    val kotlinSerialization = GradlePlugin(
        id = "kotlinx-serialization",
        module = "org.jetbrains.kotlin:kotlin-serialization:${LibraryVersions.Plugins.serialization}"
    )
}
