plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    google()

    maven("https://jitpack.io")

    gradlePluginPortal()
}

val multiplatform = "0.12.0"
val kotlin = "1.6.20-M1"
val gradle = "7.3.0-alpha03"
val nexus = "1.1.0"
val kSwift = "0.3.0"

dependencies {
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("io.github.gradle-nexus:publish-plugin:$nexus")
    implementation("dev.icerock.moko:kswift-gradle-plugin:$kSwift")
}
