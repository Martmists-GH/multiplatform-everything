package graphql

import com.martmists.multiplatform.graphql.GraphQL
import com.martmists.multiplatform.graphql.fullInterfaceType
import com.martmists.multiplatform.graphql.fullType
import graphql.schema.Character
import graphql.schema.Droid
import graphql.schema.Episode
import graphql.schema.Human
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.intellij.lang.annotations.Language
import java.util.UUID
import kotlin.reflect.typeOf
import kotlin.test.Test

class GraphQLTest {
    val luke = Human("1000", "Luke Skywalker", listOf("1002", "1003", "2000", "2001"), listOf(4, 5, 6), null, "Tatooine")
    val vader = Human("1001", "Darth Vader", listOf("1004"), listOf(4), null, "Tatooine")
    val han = Human("1002", "Han Solo", listOf("1000", "1003", "2001"), listOf(4, 5, 6), null, null)
    val leia = Human("1003", "Leia Organa", listOf("1000", "1002", "2001"), listOf(4, 5, 6), null, "Alderaan")
    val tarkin = Human("1004", "Wilhuff Tarkin", listOf("1001"), listOf(4), null, null)
    val humans = listOf(
        luke,
        vader,
        han,
        leia,
        tarkin
    )

    val threepio = Droid("2000", "C-3PO", listOf("1000", "1002", "1003", "2001"), listOf(4, 5, 6), null, "Protocol")
    val artoo = Droid("2001", "R2-D2", listOf("1000", "1002", "1003"), listOf(4, 5, 6), null, "Astromech")

    val droids = listOf(
        threepio,
        artoo
    )
    val characters = humans + droids

    val gql: GraphQL
        get() = GraphQL().apply {
            schema {
                enum(Episode.entries)
                fullInterfaceType<Character> {
                    resolver {
                        when (this) {
                            is Human -> typeOf<Human>()
                            is Droid -> typeOf<Droid>()
                        }
                    }

                    property("friends") {
                        resolver {
                            friends.map { characters.first { c -> c.id == it } }
                        }
                    }

                    property("appearsIn") {
                        resolver {
                            appearsIn.map { Episode.entries.first { e -> e.number == it } }
                        }
                    }
                }
                fullType<Human> {
                    usesInterface<Character>()

                    property("friends") {
                        resolver {
                            friends.map { characters.first { c -> c.id == it } }
                        }
                    }

                    property("appearsIn") {
                        resolver {
                            appearsIn.map { Episode.entries.first { e -> e.number == it } }
                        }
                    }
                }
                fullType<Droid> {
                    usesInterface<Character>()

                    property("friends") {
                        resolver {
                            friends.map { characters.first { c -> c.id == it } }
                        }
                    }

                    property("appearsIn") {
                        resolver {
                            appearsIn.map { Episode.entries.first { e -> e.number == it } }
                        }
                    }
                }

                query("hero") {
                    val episode = argument<Episode?>("episode")

                    resolver { ctx ->
                        when (ctx.episode()) {
                            Episode.EMPIRE -> luke
                            else -> artoo
                        }
                    }
                }

                query("human") {
                    val id = argument<String>("id")

                    resolver { ctx ->
                        humans.first { it.id == ctx.id() }
                    }
                }

                query("humanByIndex") {
                    val id = argument<Int>("id")

                    resolver { ctx ->
                        humans[ctx.id()]
                    }
                }

                query("droid") {
                    val id = argument<String>("id")

                    resolver { ctx ->
                        droids.first { it.id == ctx.id() }
                    }
                }

                query("sameUUID") {
                    val id = argument<UUID>("id")
                    resolver {
                        it.id()
                    }
                }

                scalar<UUID>(object : KSerializer<UUID> {
                    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

                    override fun serialize(encoder: Encoder, value: UUID) {
                        encoder.encodeString(value.toString())
                    }

                    override fun deserialize(decoder: Decoder): UUID {
                        return UUID.fromString(decoder.decodeString())
                    }
                })
            }
        }

    @Test
    fun testSchemaGeneration() {
        println(gql.schema.graphqls())
    }

    @Test
    fun testUUIDArg() {
        runBlocking {
            @Language("graphql")
            val query = """
query {
  sameUUID(id: "c9cde07d-9982-4d62-ac7d-1876c4908585")
}
            """.trimIndent()
            val res = gql.execute(query)

            println(res.toList())
        }
    }

    @Test
    fun testUUIDVar() {
        runBlocking {
            @Language("graphql")
            val query = """
query(${'$'}id: UUID) {
  sameUUID(id: ${'$'}id)
}
            """.trimIndent()
            val res = gql.execute(query, data=mapOf("id" to "c9cde07d-9982-4d62-ac7d-1876c4908585"))

            println(res.toList())
        }
    }

    @Test
    fun testIntArg() {
        runBlocking {
            @Language("graphql")
            val query = """
query {
  humanByIndex(id: 1) {
    name
  }
}
            """.trimIndent()
            val res = gql.execute(query)

            println(res.toList())
        }
    }

    @Test
    fun testFragments() {
        runBlocking {
            @Language("graphql")
            val query = """
query DroidFieldInFragment {
  emp: hero(episode: EMPIRE) {
    name
    ...DroidFields
  }
  all: hero {
    name
    ...DroidFields
  }
}

fragment DroidFields on Droid {
  primaryFunction
}
            """.trimIndent()
            val res = gql.execute(query, "DroidFieldInFragment", emptyMap())

            println(res.toList())
        }
    }

    @Test
    fun testIntrospection() {
        runBlocking {
            val operationName = "IntrospectionQuery"
            @Language("graphql")
            val query = """
query IntrospectionQuery {
  __schema {
    queryType { name }
    mutationType { name }
    subscriptionType { name }
    types {
      ...FullType
    }
    directives {
      name
      description
      locations
      args {
        ...InputValue
      }
    }
  }
}
fragment FullType on __Type {
  kind
  name
  description
  fields(includeDeprecated: true) {
    name
    description
    args {
      ...InputValue
    }
    type {
      ...TypeRef 
    }
    isDeprecated
    deprecationReason
  }
  inputFields {
    ...InputValue
  }
  interfaces {
    ...TypeRef
  }
  enumValues(includeDeprecated: true) {
    name
    description
    isDeprecated
    deprecationReason
  }
  possibleTypes {
    ...TypeRef
  }
}
fragment InputValue on __InputValue {
  name
  description
  type {
    ...TypeRef
  }
  defaultValue
}
fragment TypeRef on __Type {
  kind
  name
  ofType {
    kind
    name
    ofType {
      kind
      name
      ofType {
        kind
        name
        ofType {
          kind
          name
          ofType {
            kind
            name
            ofType {
              kind
              name
              ofType {
                kind
                name
              }
            }
          }
        }
      }
    }
  }
}
            """.trimIndent()
            val res = gql.execute(query, operationName, emptyMap())
            println(res.toList())
        }
    }
}
