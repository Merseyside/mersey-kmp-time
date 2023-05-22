plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenLocal()
    mavenCentral()
    google()

    gradlePluginPortal()
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
