plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}

repositories {
    mavenLocal()
    mavenCentral()
    google()

    gradlePluginPortal()
}

val multiplatform = "0.12.0"
val kotlin = "1.5.30"
val gradle = "4.2.1"
val navigation = "2.3.5"
val nexus = "1.1.0"

dependencies {
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("io.github.gradle-nexus:publish-plugin:$nexus")
}
