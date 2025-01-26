import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask

plugins {
    kotlin("jvm")
    id("com.strumenta.antlr-kotlin") version "1.0.2"
}

dependencies {
    api("com.strumenta:antlr-kotlin-runtime:1.0.2")
}

sourceSets {
    main {
        kotlin.srcDirs("build/generatedAntlr")
    }
}

tasks {
    val generateKotlinGrammarSource by registering(AntlrKotlinTask::class) {
        dependsOn("cleanGenerateKotlinGrammarSource")

        source = fileTree(layout.projectDirectory.dir("src/main/antlr")) {
            include("**/*.g4")
        }

        val pkgName = "com.martmists.multiplatform.graphql.codegen"
        packageName = pkgName

        val outDir = "generatedAntlr/${pkgName.replace(".", "/")}"
        outputDirectory = layout.buildDirectory.dir(outDir).get().asFile
    }

    compileKotlin {
        dependsOn(generateKotlinGrammarSource)
    }
}
