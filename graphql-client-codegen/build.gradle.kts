import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask

plugins {
    kotlin("jvm")
    id("com.strumenta.antlr-kotlin") version "1.0.0"
}

dependencies {
    implementation("com.strumenta:antlr-kotlin-runtime:1.0.0")
}

sourceSets {
    main {
        kotlin.srcDirs("kotlin", "build/generatedAntlr")
    }
}

tasks {
    val generateKotlinGrammarSource by registering(AntlrKotlinTask::class) {
        dependsOn("cleanGenerateKotlinGrammarSource")

        source = fileTree(layout.projectDirectory.dir("antlr")) {
            include("**/*.g4")
        }

        // We want the generated source files to have this package name
        val pkgName = "com.martmists.multiplatform.graphql.codegen"
        packageName = pkgName

        // We want visitors alongside listeners.
        // The Kotlin target language is implicit, as is the file encoding (UTF-8)
//        arguments = listOf("-visitor")

        // Generated files are outputted inside build/generatedAntlr/{package-name}
        val outDir = "generatedAntlr/${pkgName.replace(".", "/")}"
        outputDirectory = layout.buildDirectory.dir(outDir).get().asFile
    }

    compileKotlin {
        dependsOn(generateKotlinGrammarSource)
    }
}
