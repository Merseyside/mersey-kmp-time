plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    google()

    gradlePluginPortal()
    maven("https://jitpack.io")
}

dependencies {
    with(catalogGradle) {
        implementation(android.gradle)
        implementation(moko.mobileMultiplatform)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(nexusPublish)
        implementation(mersey.gradlePlugins)
    }
}
