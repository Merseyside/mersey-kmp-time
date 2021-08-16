import java.util.Base64

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()

        google()

        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://raw.githubusercontent.com/guardianproject/gpmaven/master") }
        maven { url = uri("https://maven.fabric.io/public") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    fun getExtraString(name: String) = ext[name]?.toString()

    val javadocJar by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
    }

    plugins.withId(LibraryDeps.Plugins.mavenPublish.id) {

        configure<PublishingExtension> {
            repositories {
                maven {
                    name = "sonatype"
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = System.getenv("OSSRH_USERNAME")
                        password = System.getenv("OSSRH_PASSWORD")
                    }
                }
            }

            group = LibraryVersions.Application.groupId
            version = LibraryVersions.Application.version

            publications.withType<MavenPublication> {
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set("KMM Time library")
                    description.set("Android & iOS time library")
                    url.set("https://github.com/Merseyside/mersey-kmp-time")

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("Merseyside")
                            name.set("Ivan Sablin")
                            email.set("ivanklessablin@gmail.com")
                        }
                    }
                    scm {
                        url.set("https://github.com/Merseyside/mersey-kmp-time")
                    }
                }
            }

            apply(plugin = LibraryDeps.Plugins.signing.id)

            configure<SigningExtension> {
                val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
                val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
                val signingKey: String? = System.getenv("SIGNING_KEY")?.let { base64Key ->
                    val _base = base64Key.replace("\n", "")
                    String(Base64.getDecoder().decode(_base))
                }
                if (signingKeyId != null) {
                    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
                    sign(publications)
                }
            }
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
