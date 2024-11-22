import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.21"
    id("com.android.library") version "8.6.0"  // Updating causes a weird Gradle error?
    `maven-publish`
}

group = "com.martmists.multiplatform-everything"
version = "1.1.4"

allprojects {
    repositories {
        mavenCentral()
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                implementation("io.ktor:ktor-server-core:3.0.1")
                implementation("io.ktor:ktor-server-websockets:3.0.1")
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
        }
    }
}


if (findProperty("mavenToken") != null) {
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
    }
}
