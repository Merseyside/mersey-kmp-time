import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    val projectGitUrl = "https://github.com/Merseyside/mersey-android-library"

    pom {
        name.set("Mersey time kmp library")
        description.set("Kotlin multiplatform time library")
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

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        issueManagement {
            system.set("GitHub")
            url.set("$projectGitUrl/issues")
        }

        scm {
            connection.set("scm:git:$projectGitUrl")
            developerConnection.set("scm:git:$projectGitUrl")
            url.set(projectGitUrl)
        }
    }

    publishToMavenCentral(SonatypeHost.S01)
}

