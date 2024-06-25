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
        implementation(android.gradle)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(mersey.gradlePlugins)
        implementation(maven.publish.plugin)
    }
}
