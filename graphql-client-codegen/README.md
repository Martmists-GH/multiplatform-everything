Sample GraphQL generation task for gradle:

```kotlin
package graphql

import com.martmists.multiplatform.graphql.codegen.GraphQLLexer
import com.martmists.multiplatform.graphql.codegen.GraphQLParser
import com.martmists.multiplatform.graphql.codegen.GraphQLGeneratingListener
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.io.File
import java.net.URL

abstract class GenerateGraphQLTask : DefaultTask() {
    init {
        group = "generate"
        description = "Generates GraphQL code"
    }

    @get:Input
    @get:Optional
    abstract val schemaUrl: Property<String?>

    @get:InputFile
    @get:Optional
    abstract val schemaFallback: Property<File>

    @get:Input
    abstract val packageName: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val schema = schemaUrl.orNull?.let(::loadSchema) ?: schemaFallback.get().readText()
        val output = outputDir.get().asFile

        output.mkdirs()
        output.listFiles()?.onEach { it.deleteRecursively() }

        val schemaFile = output.resolve("schema.graphql")
        schemaFile.writeText(schema)

        val lexer = GraphQLLexer(ANTLRInputStream(schema))
        val parser = GraphQLParser(CommonTokenStream(lexer))
        val listener = GraphQLGeneratingListener(output, packageName.get(), parser.ruleNames)

        try {
            ParseTreeWalker().walk(
                listener,
                parser.graphqlSchema()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun loadSchema(url: String): String? {
        try {
            val connection = URL(url).openConnection()
            connection.connect()
            return connection.getInputStream().use { it.reader().readText() }
        } catch (e: Exception) {
            return null
        }
    }
}
```
