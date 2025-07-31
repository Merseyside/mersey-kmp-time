plugins {
    `kotlin-dsl`
    kotlin("jvm") version catalogGradle.versions.kotlin.get()
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
