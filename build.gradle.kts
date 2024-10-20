plugins {
    kotlin("multiplatform") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
}

group = "com.martmists.multiplatform"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        nodejs()
        browser()
    }
    mingwX64()
    linuxX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                implementation("io.ktor:ktor-server-core:3.0.0")
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
