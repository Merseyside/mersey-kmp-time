plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    google()

    gradlePluginPortal()
}

dependencies {
    with(catalogGradle) {
        implementation(android.gradle.stable)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(mersey.gradlePlugins)
        implementation(maven.publish.plugin)
    }
}
