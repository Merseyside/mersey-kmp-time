plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    google()

    gradlePluginPortal()
}

val multiplatform = "0.12.0"
val kotlin = "1.6.0"
val gradle = "7.0.3"
val nexus = "1.1.0"

dependencies {
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("io.github.gradle-nexus:publish-plugin:$nexus")
}
