import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
    id("com.android.library") version "8.6.1"  // Updating causes a weird Gradle error?
    `maven-publish`
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.vanniktech.maven.publish") version "0.31.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

group = "com.martmists.multiplatform-everything"
version = "1.3.0"

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}


kotlin {
    jvm("desktop")
    js(IR) {
        nodejs()
        browser()
    }
    wasmJs {
        browser()
        nodejs()
        binaries.library()
    }
    androidTarget()
    mingwX64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
                implementation("io.ktor:ktor-server-core:3.1.3")
                implementation("io.ktor:ktor-server-websockets:3.1.3")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }

        val wasmJsMain by getting {
            dependsOn(commonMain)
        }

        val jvmMain by creating {
            dependsOn(commonMain)
        }

        val jvmTest by creating {
            dependsOn(commonTest)
        }

        val desktopMain by getting {
            dependsOn(jvmMain)
        }

        val desktopTest by getting {
            dependsOn(jvmTest)
        }

        val androidMain by getting {
            dependsOn(jvmMain)
        }

        val androidUnitTest by getting {
            dependsOn(jvmTest)
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        linuxX64Main {
            dependsOn(nativeMain)
        }

        mingwX64Main {
            dependsOn(nativeMain)
        }
    }
}

android {
    compileSdk = 35
    namespace = "com.martmists.multiplatformeverything"
    ndkVersion = "21.4.7075529"

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks {
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
            freeCompilerArgs.add("-XXLanguage:+CustomEqualsInValueClasses")
        }
    }
}


if (findProperty("mavenToken") != null) {
    fun MavenPom.configure() {
        name = "NDArray.simd"
        description = "Kotlin/Multiplatform NDArray with SIMD optimizations and low memory footprint"
        url = "https://github.com/martmists-gh/multiplatform-everything"

        licenses {
            license {
                name = "3-Clause BSD NON-AI License"
                url = "https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-BSD3"
                distribution = "repo"
            }
        }

        developers {
            developer {
                id = "Martmists"
                name = "Martmists"
                url = "https://github.com/martmists-gh"
            }
        }

        scm {
            url = "https://github.com/martmists-gh/multiplatform-everything"
        }
    }

    publishing {
        repositories {
            maven {
                name = "Releases"
                url = uri("https://maven.martmists.com/releases")
                credentials {
                    username = "admin"
                    password = project.ext["mavenToken"]!! as String
                }

            }
        }

        publications {
            withType<MavenPublication> {
                version = project.version as String
                pom {
                    configure()
                }
            }
        }
    }

    mavenPublishing {
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
        coordinates(group as String, name, version as String)
        signAllPublications()

        pom {
            configure()
        }
    }
}
